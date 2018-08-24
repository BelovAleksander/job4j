package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.08.18
 */

public class DBStore implements Store {
    private static BasicDataSource source;
    private final static DBStore INSTANCE = new DBStore();
    private int count = 1;

    private DBStore() {

    }

    public void setSource(BasicDataSource bds) {
        source = bds;
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    public static boolean isValid(String login, String password) {
        boolean result = false;
        String sql = String.format("SELECT EXISTS(SELECT * FROM users WHERE login = '%s' AND password = '%s');",
                login, password);
        try {
            Connection conn = source.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            result = rs.getBoolean(1);
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User findByLogin(final String login) {
        String sql = String.format("SELECT * FROM users WHERE login = '%s'", login);
        return getUser(sql);
    }

    private static User getUser(final String sql) {
        User result = null;
        try (Connection conn = source.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String userId = rs.getString("id");
            String userName = rs.getString("user_name");
            String userLogin = rs.getString("login");
            String userEmail = rs.getString("email");
            String userPassword = rs.getString("password");
            Date userCreateDate = rs.getDate("createDate");
            String userRole = rs.getString("role");
            result = new User(Integer.parseInt(userId), userName, userLogin, userEmail,
                    userPassword, userCreateDate.getTime(), userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public synchronized void add(final String name, final String login, final String email,
                                 final String password, final String role) {
        String sql =
                "INSERT INTO users(id, user_name, login, email, password, createDate, role) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = source.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, this.count);
            ps.setString(2, name);
            ps.setString(3, login);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setDate(6, new Date(System.currentTimeMillis()));
            ps.setString(7, role);
            ps.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        this.count++;
    }

    @Override
    public void update(final User user, final String name, final String login,
                       final String email, final String password, final String role) {
        String sql = "UPDATE users SET user_name = ?, login = ?, email = ?, password = ?, role = ? WHERE id = ?;";
        try (Connection conn = source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, login);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.setInt(6, user.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = source.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(final int id) {
        String sql = String.format("SELECT * FROM users WHERE id = %s", id);
        return getUser(sql);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try (Connection conn = source.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                String userId = rs.getString("id");
                String userName = rs.getString("user_name");
                String userLogin = rs.getString("login");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                Date userCreateDate = rs.getDate("createDate");
                String userRole = rs.getString("role");
                User user = new User(Integer.parseInt(userId), userName, userLogin,
                        userEmail, userPassword, userCreateDate.getTime(), userRole);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> findAllEmails() {
        List<String> result = new ArrayList<>();
        String sql = "SELECT email FROM users;";
        try (Connection conn = source.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                String email = rs.getString("email");
                result.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
