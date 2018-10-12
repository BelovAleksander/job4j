package ru.job4j.items.logic;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.items.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.09.18
 */
public class ItemStorage {
    private static final ItemStorage INSTANCE = new ItemStorage();
    private static final SessionFactory FACTORY =  new Configuration().configure().buildSessionFactory();
    private static final Logger LOG = Logger.getLogger("APP1");

    private ItemStorage() {

    }
    public static ItemStorage getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = FACTORY.openSession();
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
        final List<Item> unperformed = new ArrayList<>();
        for (Item item : getAllItems()) {
            if (!item.isDone()) {
                unperformed.add(item);
            }
        }
        return unperformed;
    }

    public String changePerformance(final int id) {
        return this.tx(session -> {
            Item item = session.load(Item.class, id);
            item.setDone(!item.isDone());
            return null;
        });
    }

    public void closeFactory() {
        FACTORY.close();
    }
}
