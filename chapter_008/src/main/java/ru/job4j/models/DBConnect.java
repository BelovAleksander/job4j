package ru.job4j.models;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.logic.DBStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

public class DBConnect implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String driver = context.getInitParameter("SQLDriver");
        String url = context.getInitParameter("url");
        String user = context.getInitParameter("user");
        String password = context.getInitParameter("password");
        String dbName = context.getInitParameter("dbName");
        String tableName = context.getInitParameter("tableName");

        initDB(url, user, password, dbName, driver, tableName);
        BasicDataSource source = new BasicDataSource();

        source.setDriverClassName(driver);
        source.setUrl(url + dbName);
        source.setUsername(user);
        source.setPassword(password);
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);

        DBStore.getInstance().setSource(source);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {

    }

    private void initDB(final String url, final String user, final String password,
                                  final String dbName, final String driver, final String tableName) {
        String sql = String.format(
                "SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = '%s');", dbName
        );
        try (Connection conn = DriverManager
                .getConnection(String.format("%s?user=%s&password=%s", url, user, password));
             ResultSet rs = conn.createStatement().executeQuery(sql);
             Statement st = conn.createStatement()) {
            Class.forName(driver);
            rs.next();
            boolean haveBase = rs.getBoolean(1);
            if (!haveBase) {
                sql = String.format("CREATE database %s", dbName);
                st.executeUpdate(sql);
                createTable(url, user, password, dbName, tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(final String url, final String user, final String password,
                             final String dbName, final String tableName) {
        StringBuilder sb = new StringBuilder("CREATE table ")
                .append(tableName)
                .append("(id integer PRIMARY KEY, user_name varchar(30), login varchar(30), ")
                .append("email varchar(30), createDate date);");
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password)) {   //???
            conn.createStatement().executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
