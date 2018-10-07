package ru.job4j.listeners;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.logic.HibernateManager;
import ru.job4j.logic.ItemStorage;

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

    private BasicDataSource getSource(final String url) {
        BasicDataSource source = new BasicDataSource();
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
        ItemStorage.getInstance().openFactory();
        BasicDataSource source = getSource("jdbc:postgresql://localhost/");

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
                BasicDataSource sourceForTable = getSource("jdbc:postgresql://localhost/items_db");
                createTable(sourceForTable);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(BasicDataSource source) throws SQLException {
        String sql = "CREATE TABLE items(id integer PRIMARY KEY, description varchar(100), create_date date, done boolean);";
        Connection conn = source.getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
        st.close();
        conn.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateManager.getInstance().closeFactory();
        ItemStorage.getInstance().closeFactory();
        BasicDataSource source = getSource("jdbc:postgresql://localhost/items_db");
        String sql = "DELETE FROM items;";
        try {
            Connection conn = source.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
