package ru.job4j.logic;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.09.18
 */
public class ItemStorage {
    private static final ItemStorage INSTANCE = new ItemStorage();
    private static SessionFactory factory;
    private AtomicInteger countID = new AtomicInteger(0);
    private static final Logger LOG = Logger.getLogger("APP1");

    private ItemStorage() {

    }
    public static ItemStorage getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    public Item add(final Item item) {
        return this.tx(session -> {
            item.setId(countID.getAndIncrement());
            session.save(item);
            return item;
        });
    }

    public List<Item> getAllItems() {
        return this.tx(session ->
                (List<Item>) session.createQuery("from Item").list()
            );
    }

    public List<Item> getAllUnperformedItems() {
        return this.tx(session -> {
            List<Item> allItems = session.createQuery("from Item").list();
            List<Item> unperformed = new ArrayList<>();
            for (Item item : allItems) {
                if (!item.isDone()) {
                    LOG.info(item.getDesc());
                    unperformed.add(item);
                }
            }
            return unperformed;
        });
    }

    public String changePerformance(final int id) {
        return this.tx(session -> {
            Item item = session.load(Item.class, id);
            item.setDone(!item.isDone());
            return null;
        });
    }

    public void openFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void closeFactory() {
        factory.close();
    }
}
