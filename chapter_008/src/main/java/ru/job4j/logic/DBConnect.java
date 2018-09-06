package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

public class DBConnect implements ServletContextListener {
    private String url;
    private String dbName;
    private String user;
    private String password;

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String driver = context.getInitParameter("SQLDriver");
        this.url = context.getInitParameter("url");
        this.user = context.getInitParameter("user");
        this.password = context.getInitParameter("password");
        this.dbName = context.getInitParameter("dbName");

        initDB(driver);
        BasicDataSource source = new BasicDataSource();

        source.setDriverClassName(driver);
        source.setUrl(url + dbName);
        source.setUsername(user);
        source.setPassword(password);
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);

        DBStore.getInstance().init(source);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password)) {   //???
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM users;");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDB(final String driver) {
        String sql = String.format(
                "SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = '%s');", dbName
        );
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager
                .getConnection(String.format("%s?user=%s&password=%s", url, user, password));
             ResultSet rs = conn.createStatement().executeQuery(sql);
             Statement st = conn.createStatement()) {
            rs.next();
            boolean haveBase = rs.getBoolean(1);
            if (!haveBase) {
                sql = String.format("CREATE database %s", dbName);
                st.executeUpdate(sql);
            }
            createTable(url, user, password, dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(final String url, final String user, final String password, final String dbName) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS users")
                .append("(id integer PRIMARY KEY, user_name varchar(30), login varchar(30), ")
                .append("email varchar(30), password varchar(20), createDate date, role varchar(5), ")
                .append("city varchar(30), country varchar(30));");
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password)) {   //???
            Statement st = conn.createStatement();
            st.executeUpdate(sb.toString());
            String sql =
                    "INSERT INTO users(id, user_name, login, email, password, createDate, role, city, country)"
                            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setString(2, "alexander");
            ps.setString(3, "admin");
            ps.setString(4, "example@mail.com");
            ps.setString(5, "123");
            ps.setDate(6, new Date(System.currentTimeMillis()));
            ps.setString(7, "admin");
            ps.setString(8, "Norilsk");
            ps.setString(9, "Russia");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
