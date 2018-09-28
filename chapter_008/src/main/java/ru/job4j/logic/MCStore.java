package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 08.09.18
 */
public class MCStore {
    private static BasicDataSource source;
    private static final MCStore INSTANCE = new MCStore();
    private AtomicInteger countId = new AtomicInteger(1);

    private MCStore() {

    }

    public void init(BasicDataSource bds) {
        source = bds;
    }

    public static MCStore getInstance() {
        return INSTANCE;
    }

    public int addUser(final UserMC user) {
        String sql = "INSERT INTO users(id, login, password, address_id, role_id) VALUES(?, ?, ?, ?, ?);";
        int id = countId.getAndIncrement();
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAddress().getId());
            ps.setInt(5, user.getRole().getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setId(id);
        addValuesToMusicPref(user);
        return id;
    }

    public void updateUser(int id, final UserMC user) {
        String sql = "UPDATE users SET login = ?, password = ?, address_id = ?, role_id = ? WHERE id = ?";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAddress().getId());
            ps.setInt(4, user.getRole().getId());
            ps.setInt(5, id);
            ps.execute();
            ps.close();
            addValuesToMusicPref(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserMC> findAllUsers() {
        List<UserMC> list = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                list.add(createUser(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<UserMC> findUsersByParameters(String parameter, int value) {
        String sql = String.format("SELECT * FROM users WHERE %s = %s;", parameter, value);
        List<UserMC> result = new ArrayList<>();
        try {
            Connection conn = source.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                result.add(createUser(rs));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserMC findUserById(final int id) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE id = %s;", id);
        Connection conn = source.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql);
        rs.next();
        UserMC user = createUser(rs);
        conn.close();
        rs.close();
        return user;
    }


    public int addElement(final String table, final Entity entity) {
        String sql = String.format("INSERT INTO %s(name) VALUES(?);", table);
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findElementId(table, entity.getName());
    }

    public void updateElement(final String table, final Entity entity, int id) {
        String sql = String.format("UPDATE %s SET name = ? WHERE id = ?;", table);
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setInt(2, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteElement(final String table, final int id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", table);
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllElements(final String table) {
        List<String> list = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s;", table);
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                list.add(name);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Entity findElementById(final String table, final int id) {
        Entity res = new Entity();
        res.setId(id);
        String sql = String.format("SELECT * FROM %s WHERE id = %s;", table, id);
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            String name = rs.getString("name");
            res.setName(name);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int findElementId(final String table, final String name) {
        int res = -1;
        String sql = String.format("SELECT id FROM %s WHERE name = '%s';", table, name);
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            res = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int findUserByLogin(final String login) {
        int result = -1;
        String sql = String.format("SELECT id FROM users WHERE login = '%s';", login);
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            result = rs.getInt("id");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> findAllLogins() {
        List<String> logins = new ArrayList<>();
        String sql = "SELECT login FROM users;";
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                String login = rs.getString("login");
                logins.add(login);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logins;
    }

    private UserMC createUser(final ResultSet rsUsers) throws SQLException {
        int id = rsUsers.getInt("id");
        String login = rsUsers.getString("login");
        String password = rsUsers.getString("password");
        int addressID = rsUsers.getInt("address_id");
        int roleID = rsUsers.getInt("role_id");
        List<Music> musicList = new ArrayList<>();

        String sql = String.format("SELECT music_id FROM music_pref WHERE user_id = %s", id);
        try (Connection conn = source.getConnection()) {
            ResultSet rsMusicPref = conn.createStatement().executeQuery(sql);
            while (rsMusicPref.next()) {
                int musicID = rsMusicPref.getInt("music_id");
                Music music = new Music(musicID, findElementById("music", musicID).getName());
                musicList.add(music);
            }
            rsMusicPref.close();
        }
        UserMC user = new UserMC(login, password,
                new Address(addressID, findElementById("addresses", addressID).getName()),
                new Role(roleID, findElementById("roles", roleID).getName()), musicList);
        user.setId(id);
        return user;
    }

    private void addValuesToMusicPref(final UserMC user) {
        String sql = "INSERT INTO music_pref(user_id, music_id) VALUES(?, ?);";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Music el : user.getMusic()) {
                ps.setInt(1, user.getId());
                ps.setInt(2, el.getId());
                ps.execute();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteValuesFromMusicPref(final int id) {
        String sql = "DELETE FROM music_pref WHERE user_id = ?";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getUsersIdFromMusicPref(int musicID) {
        String sql = String.format("SELECT user_id FROM music_pref WHERE music_id = %s;", musicID);
        List<Integer> result = new ArrayList<>();
        try (Connection conn = source.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getInt("user_id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
