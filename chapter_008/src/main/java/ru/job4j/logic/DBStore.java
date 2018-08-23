package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStore implements Store {
    private static BasicDataSource source;
    private final static DBStore INSTANCE = new DBStore();
    private int count = 0;

    public void setSource(BasicDataSource bds) {
        source = bds;
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized void add(final String name, final String login, final String email) {
        String sql = "INSERT INTO users(id, user_name, login, email, createDate) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = source.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, this.count);
            ps.setString(2, name);
            ps.setString(3, login);
            ps.setString(4, email);
            ps.setDate(5, new Date(System.currentTimeMillis()));
            ps.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        this.count++;
    }

    @Override
    public void update(final User user, final String name, final String login, final String email) {
        String sql = "UPDATE users SET user_name = ?, login = ?, email = ? WHERE id = ?;";
        try (Connection conn = source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, login);
            ps.setString(3, email);
            ps.setInt(4, user.getId());
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
        User result = null;
        String sql = String.format("SELECT * FROM users WHERE id = %s", id);
        try (Connection conn = source.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String userId = rs.getString("id");
            String userName = rs.getString("user_name");
            String userLogin = rs.getString("login");
            String userEmail = rs.getString("email");
            Date userCreateDate = rs.getDate("createDate");
            result = new User(Integer.parseInt(userId), userName, userLogin, userEmail, userCreateDate.getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
                Date userCreateDate = rs.getDate("createDate");
                User user = new User(Integer.parseInt(userId), userName, userLogin,
                        userEmail, userCreateDate.getTime());
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
