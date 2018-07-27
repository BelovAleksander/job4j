package ru.job4j.tracker;

import java.sql.*;
import java.util.*;


/**
 * @author whiterabbit.nsk
 * @since 27.07.2018
 * Класс Tracker предоставляет набор методов
 * для обработки заявок.
 */
public class Tracker implements AutoCloseable {
    private final String url = "jdbc:postgresql://localhost:5432/tracker";
    private final String usertitle = "postgres";
    private final String password = "password";
    private Connection connection = null;
    private boolean haveBase = false;

    public Tracker() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/?user=postgres&password=password");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT EXISTS (SELECT datname FROM pg_catalog.pg_database WHERE datname = 'tracker');");
            rs.next();
            haveBase =  rs.getBoolean(1);
            rs.close();

            if (!haveBase) {
                st.executeUpdate("CREATE DATABASE tracker");
            }
            st.close();
            this.connection.close();

            this.connection = DriverManager.getConnection(url, usertitle, password);
            Statement st2 = connection.createStatement();
            st2.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS items(id varchar(20) PRIMARY KEY, title varchar(20), description text);");
            st2.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Хранит рандомное значение.
     */
    private static final Random RN = new Random();

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
