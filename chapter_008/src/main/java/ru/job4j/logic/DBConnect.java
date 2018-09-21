package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.09.18
 * Класс-слушатель развертывает БД и таблицы.
 */
public class DBConnect implements ServletContextListener {
    private String url;
    private String dbName;
    private String mcDBName;
    private String user;
    private String password;
    private String driver;

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        this.driver = context.getInitParameter("SQLDriver");
        this.url = context.getInitParameter("url");
        this.user = context.getInitParameter("user");
        this.password = context.getInitParameter("password");
        this.dbName = context.getInitParameter("dbName");
        this.mcDBName = context.getInitParameter("mcDBName");

        initDB(dbName);
        initDB(mcDBName);
        BasicDataSource sourceForDB = getSource(dbName);
        BasicDataSource sourceForMCDB = getSource(mcDBName);

        DBStore.getInstance().init(sourceForDB);
        MCStore.getInstance().init(sourceForMCDB);
    }

    /**
     * Настройки пула коннектов.
     * @param db БД
     * @return пул коннектов
     */
    private BasicDataSource getSource(final String db) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(driver);
        source.setUrl(url + db);
        source.setUsername(user);
        source.setPassword(password);
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
        return source;
    }

    /**
     * Класс проверяет наличие БД. Если ее не обнаружено - содает новую БД и таблицы.
     * @param db имя БД
     */
    private void initDB(final String db) {
        String sql = String.format(
                "SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = '%s');", db
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
                sql = String.format("CREATE database %s", db);
                st.executeUpdate(sql);
                if (db.equals("servlets_db")) {
                    createTableUsers();
                } else if (db.equals("music_court")) {
                    createTablesForMC();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Создает таблицу users в БД servlets_db.
     */
    private void createTableUsers() {
        StringBuilder sb = new StringBuilder("CREATE TABLE users")
                .append("(id integer PRIMARY KEY, user_name varchar(30), login varchar(30), ")
                .append("email varchar(30), password varchar(20), createDate date, role varchar(5), ")
                .append("city varchar(30), country varchar(30));");
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password)) {
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

    /**
     * Создает в БД music_court таблицы music, roles, address, users, music_pref.
     */
    private void createTablesForMC() throws SQLException {  //todo: переделать на Prepared Statement
        List<String> updates = new ArrayList<>();
        updates.add("CREATE TABLE music(id serial PRIMARY KEY, name varchar(20), UNIQUE(name));");
        updates.add("CREATE TABLE roles(id serial PRIMARY KEY, name varchar(20), UNIQUE(name));");
        updates.add("CREATE TABLE addresses(id serial PRIMARY KEY, name varchar(100), UNIQUE (name))");
        updates.add("CREATE TABLE users(id integer PRIMARY KEY, login varchar(30), "
                + "password varchar(20), address_id integer REFERENCES addresses(id), "
                + "role_id integer REFERENCES roles(id), UNIQUE(login));");
        updates.add("CREATE TABLE music_pref(user_id integer REFERENCES users(id), "
                + "music_id integer REFERENCES music(id), CONSTRAINT pk PRIMARY KEY(user_id, music_id));");
        executeUpdates(updates);
        fillTablesForMC();
    }

    /**
     * Заполняет таблицы roles, music, addresses, users, music_pref.
     * @throws SQLException
     */
    private void fillTablesForMC() throws SQLException {
        Connection conn = DriverManager.getConnection(url + mcDBName, user, password);
        String sql = "INSERT INTO roles(name) VALUES(?), (?), (?);";
        Object[] values = new String[]{"admin", "mandatory", "user"};
        prepareStatement(sql, values, conn);

        sql = "INSERT INTO music(name) VALUES(?), (?), (?), (?), (?);";
        values = new String[]{"rock", "rap", "drum & bass", "jazz", "classic"};
        prepareStatement(sql, values, conn);

        sql = "INSERT INTO addresses(name) VALUES(?);";
        values = new String[]{"Russia, Norilsk"};
        prepareStatement(sql, values, conn);

        sql = "INSERT INTO users(id, login, password, address_id, role_id) VALUES(?, ?, ?, ?, ?);";
        values = new Object[]{0, "admin", "123", 1, 1};
        prepareStatement(sql, values, conn);

        sql = "INSERT INTO music_pref(user_id, music_id) VALUES(0, ?);";
        values = new Integer[]{1};
        prepareStatement(sql, values, conn);
        conn.close();
    }

    /**
     * Выполняет список запросов.
     * @param updates список запросов
     */
    private void executeUpdates(final List<String> updates) {
        try (Connection connection = DriverManager.getConnection(url + mcDBName, user, password)) {
            Statement st = connection.createStatement();
            for (String sql : updates) {
                st.executeUpdate(sql);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выполняет запрос, связанный с инициализацией таблиц.
     * @param query sql-запрос
     * @param values список используемых в запросе значений
     * @param conn соединение с БД
     * @throws SQLException
     */
    private void prepareStatement(String query, Object[] values, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        for (int i = 0; i < values.length; i++) {
            ps.setObject(i + 1, values[i]);
        }
        ps.execute();
        ps.close();
    }
    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        try {
            cleanTablesMC();
            cleanTableUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет из БД servlets_db всех пользователей, кроме администратора.
     * @throws SQLException
     */
    private void cleanTableUsers() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password)) {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM users WHERE id != 0;");
            st.close();
        }
    }

    /**
     * Удаляет из БД music_court все данные, кроме заданных по умолчанию.
     * @throws SQLException
     */
    private void cleanTablesMC() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url + mcDBName, user, password)) {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM music_pref WHERE user_id > 0;");
            st.executeUpdate("DELETE FROM users WHERE id > 0;");
            st.executeUpdate("DELETE FROM roles WHERE id > 3;");
            st.executeUpdate("DELETE FROM music WHERE id > 5;");
            st.executeUpdate("DELETE FROM addresses WHERE id > 1;");
            st.close();
        }
    }
}
