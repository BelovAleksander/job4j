package ru.job4j.listeners;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.cars.logic.HibernateManager;
import ru.job4j.items.logic.ItemStorage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.09.18
 */
public class DBInit implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger("APP2");

    private BasicDataSource getSource(final String url) {
        final BasicDataSource source = new BasicDataSource();
        source.setUrl(url);
        source.setDriverClassName("org.postgresql.Driver");
        source.setUsername("postgres");
        source.setPassword("password");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        return source;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final BasicDataSource source = getSource("jdbc:postgresql://localhost/");

        String sql = "SELECT EXISTS (SELECT datname FROM pg_catalog.pg_database WHERE datname = 'items_db');";
        try {
            Connection conn = source.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            boolean dbExist = rs.getBoolean(1);
            if (!dbExist) {
                sql = "CREATE database items_db;";
                Statement st = conn.createStatement();
                st.executeUpdate(sql);
                st.close();
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateManager.getInstance().closeFactory();
        ItemStorage.getInstance().closeFactory();
    }
}
