package ru.job4j.tracker;

import java.sql.*;
import java.util.*;

/**
 * @author Alexander Belov(whiterabbit.nsk@gmail.com)
 * @since 06.08.2018
 * Класс Tracker предоставляет набор методов
 * для обработки заявок.
 */
public class Tracker implements AutoCloseable {
    private final String config = "tracker";
    private final String url;
    private final String user;
    private final String password;
    private final String scriptCheckDB;
    private final String scriptCreateDB;
    private final String scriptCreateTable;
    private Connection connection;
    private boolean haveBase = false;
    private static final Random RN = new Random();

    public Tracker() {
        ResourceBundle resource = ResourceBundle.getBundle(config);
        String driver = resource.getString("driver");
        this.user = resource.getString("user");
        this.password = resource.getString("password");
        this.url = resource.getString("url");
        this.scriptCheckDB = resource.getString("checkDBExistenceScript");
        this.scriptCreateDB = resource.getString("createDBScript");
        this.scriptCreateTable = resource.getString("createTableIfNotExistScript");

        /**
         * Здесь решил не испоьзовать try-with-resources, т.к. Connection придется открывать
         * в каждом методе.
         */
        Statement st = null;
        try {
            checkDB(this.user, this.password);
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            st = connection.createStatement();
            st.executeUpdate(scriptCreateTable);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkDB(final String user, final String password) throws SQLException {
        String urlDef = String.format("jdbc:postgresql://localhost/?user=%s&password=%s", user, password);
        try (Connection conn = DriverManager.getConnection(urlDef);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(scriptCheckDB)) {

            rs.next();
            haveBase = rs.getBoolean(1);
            if (!haveBase) {
                st.executeUpdate(scriptCreateDB);
            }
        }
    }


    /**
     * Результат работы метода указывает на то, что массив заявок пуст.
     * @return пустой или нет
     */
    public boolean dataEmpty() {
        boolean result = false;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM items;");
            if (!rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Метод, реализующий добавление заявок в БД.
     *
     * @param item новая заявка.
     * @return true, если успешно
     */
    public Item add(final Item item) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO items(id, title, description) VALUES(?,?,?);");
            item.setId(generateId());
            ps.setString(1, item.getId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Метод заменяет указанную заявку из массива на новую.
     *
     * @param id   номер заменяемой заявки.
     * @param item новая заявка.
     */
    public final void replace(final String id, final Item item) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE items SET title = ?, description = ? WHERE id = ?;");
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setString(3, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод удаляет значение из базы данных.
     *
     * @param id индекс удаляемого значения.
     */
    public final void delete(final String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM items WHERE id = ?;");
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает все заявки, находящиеся в БД.
     *
     * @return массив заявок.
     */
    public final ArrayList<Item> findAll() {
        ArrayList<Item> array = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM items;");
            while (rs.next()) {
                Item item = new Item(rs.getString("id"), rs.getString("title"),
                        rs.getString("description"));
                array.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * Метод возвращает все элементы у которых
     * title совпадает со входящим значением.
     *
     * @param key искомое значение.
     * @return массив с подходящими значениями.
     */
    public final ArrayList<Item> findByName(final String key) {
        ArrayList<Item> res = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM items WHERE title = ?;");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(new Item(rs.getString("id"), rs.getString("title"),
                        rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Метод находит элемент в БД по id.
     *
     * @param id искомый id.
     * @return найденный элемент или null.
     */
    public final Item findById(final String id) {
        Item item = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM items WHERE id = ?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item = new Item(rs.getString("id"), rs.getString("title"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание.
     * Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    public final String generateId() {
        return String.valueOf(RN.nextInt(100000));
    }

    @Override
    public void close() {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM items;");
            st.close();
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
