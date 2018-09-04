package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.Personality;
import ru.job4j.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.08.18
 */

public class DBStore {
    private static BasicDataSource source;
    private final static DBStore INSTANCE = new DBStore();
    private final AtomicInteger countId = new AtomicInteger(1);

    private DBStore() {

    }

    public void setSource(BasicDataSource bds) {
        source = bds;
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    public boolean isValid(String login, String password) {
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

    public User findByLogin(final String login) {
        String sql = String.format("SELECT * FROM users WHERE login = '%s'", login);
        return getUser(sql);
    }

    private User getUser(final String sql) {
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
            String userCity = rs.getString("city");
            String userCountry = rs.getString("country");

            Personality personality = new Personality(userName, userLogin, userEmail, userPassword);
            result = new User(Integer.parseInt(userId), personality, userCreateDate.getTime(),
                    userRole, userCity, userCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void add(final Personality personality,
                                 final String role, final String city, final String country) {
        String sql =
                "INSERT INTO users(id, user_name, login, email, password, createDate, role, city, country) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = source.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, this.countId.get());
            ps.setString(2, personality.getName());
            ps.setString(3, personality.getLogin());
            ps.setString(4, personality.getEmail());
            ps.setString(5, personality.getPassword());
            ps.setDate(6, new Date(System.currentTimeMillis()));
            ps.setString(7, role);
            ps.setString(8, city);
            ps.setString(9, country);
            ps.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        this.countId.getAndIncrement();
    }

    public void update(final User user, final Personality personality, final String role, final String city, final String country) {
        String sql =
                "UPDATE users SET user_name = ?, login = ?, email = ?, password = ?, role = ?, city = ?,"
                        + " country = ? WHERE id = ?;";
        try (Connection conn = source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, personality.getName());
            ps.setString(2, personality.getLogin());
            ps.setString(3, personality.getEmail());
            ps.setString(4, personality.getPassword());
            ps.setString(5, role);
            ps.setString(6, city);
            ps.setString(7, country);
            ps.setInt(8, user.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public User findById(final int id) {
        String sql = String.format("SELECT * FROM users WHERE id = %s", id);
        return getUser(sql);
    }

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
                String userCity = rs.getString("city");
                String userCountry = rs.getString("country");

                Personality personality = new Personality(userName, userLogin, userEmail, userPassword);
                User user = new User(Integer.parseInt(userId), personality, userCreateDate.getTime(),
                        userRole, userCity, userCountry);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> findAllElements(String column, String query) {
        List<String> result = new ArrayList<>();
        try (Connection conn = source.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(query)) {
            while (rs.next()) {
                String element = rs.getString(column);
                result.add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
