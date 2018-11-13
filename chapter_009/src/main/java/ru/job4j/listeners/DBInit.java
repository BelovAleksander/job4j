package ru.job4j.listeners;

import ru.job4j.cars.logic.HibernateManager;
import ru.job4j.items.logic.ItemStorage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.09.18
 */
public class DBInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateManager.closeFactory();
        ItemStorage.getInstance().closeFactory();
    }

}
