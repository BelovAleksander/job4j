package ru.job4j.logic;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    public Item add(Item item) {
        LOG.info("storage | add");
        item.setId(countID.getAndIncrement());
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        LOG.info("id: " + item.getId() + " desc: " + item.getDesc() + " date: "
                + item.getCreated() + " done: " + item.isDone());
        return item;
    }

    public List<Item> getAllItems() {
        LOG.info("storage | get all");
        Session session = factory.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("from Item").list();
        for (Item item : items) {
            LOG.info(item.getDesc());
        }
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public List<Item> getAllUnperformedItems() {
        LOG.info("storage | get unperformed");
        Session session = factory.openSession();
        session.beginTransaction();
        List<Item> allItems = session.createQuery("from Item").list();
        List<Item> unperformed = new ArrayList<>();
        for (Item item : allItems) {
            if (!item.isDone()) {
                LOG.info(item.getDesc());
                unperformed.add(item);
            }
        }
        session.getTransaction().commit();
        session.close();
        return unperformed;
    }

    public void changePerformance(int id) {
        LOG.info("storage | changePerformance");
        Session session = factory.openSession();
        session.beginTransaction();
        Item item = session.load(Item.class, id);
        item.setDone(!item.isDone());
        LOG.info(item.isDone());
        session.getTransaction().commit();
        session.close();
    }

    public void openFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void closeFactory() {
        factory.close();
    }
}
