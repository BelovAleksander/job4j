package ru.job4j.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Personality;
import ru.job4j.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.09.18
 */
public class DBStoreTest {
    private DBStore store = DBStore.getInstance();
    private BasicDataSource source = mock(BasicDataSource.class);
    private Connection connection = mock(Connection.class);
    private Statement statement = mock(Statement.class);
    private ResultSet resultSet = mock(ResultSet.class);
    private PreparedStatement preparedStatement = mock(PreparedStatement.class);

    private String name = "name";
    private String login = "login";
    private String email = "email";
    private String password = "password";
    private String role = "role";
    private String city = "city";
    private String country = "country";
    private Date date = new Date(1000);
    private int id = 1;
    Personality personality = new Personality(name, login, email, password);

    @Before
    public void init() throws SQLException {
        store.init(source);
        when(source.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    public void whenLoginAndPasswordIsValid() throws SQLException {
        String login = "test";
        String password = "password";
        String sql = String.format("SELECT EXISTS(SELECT * FROM users WHERE login = '%s' AND password = '%s');",
                login, password);
        boolean expected = true;

        when(connection.createStatement().executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean(1)).thenReturn(expected);

        boolean result = store.isValid(login, password);
        verify(resultSet).next();
        verify(resultSet).close();
        verify(connection).close();
        assertThat(result, is(expected));
    }

    @Test
    public void whenSendSQLThenReturnUser() throws SQLException {
        String sql = "This should return user";

        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn(id + "");
        when(resultSet.getString("user_name")).thenReturn(name);
        when(resultSet.getString("login")).thenReturn(login);
        when(resultSet.getString("email")).thenReturn(email);
        when(resultSet.getString("password")).thenReturn(password);
        when(resultSet.getDate("createDate")).thenReturn(date);
        when(resultSet.getString("role")).thenReturn(role);
        when(resultSet.getString("city")).thenReturn(city);
        when(resultSet.getString("country")).thenReturn(country);

        User expected = new User(1, personality, date.getTime(), role, city, country);
        User result = store.getUser(sql);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddUser() throws SQLException {
        String sql = "INSERT INTO users(id, user_name, login, email, password, createDate, role, city, country) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);

        store.add(new Personality(name, login, email, password), role, city, country);

        verify(preparedStatement).setInt(1, id);
        verify(preparedStatement).setString(2, name);
        verify(preparedStatement).setString(3, login);
        verify(preparedStatement).setString(4, email);
        verify(preparedStatement).setString(5, password);
        verify(preparedStatement).setString(7, role);
        verify(preparedStatement).setString(8, city);
        verify(preparedStatement).setString(9, country);
        verify(preparedStatement).execute();
    }

    @Test
    public void whenUpdateUser() throws SQLException {
        String sql =
                "UPDATE users SET user_name = ?, login = ?, email = ?, password = ?, role = ?, city = ?,"
                        + " country = ? WHERE id = ?;";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        User old = new User(id, new Personality(
                "oldName", "oldLogin", "oldEmail", "oldPassword"),
                date.getTime(), role, city, country);
        store.update(old, personality, role, city, country);

        verify(preparedStatement).setString(1, name);
        verify(preparedStatement).setString(2, login);
        verify(preparedStatement).setString(3, email);
        verify(preparedStatement).setString(4, password);
        verify(preparedStatement).setString(5, role);
        verify(preparedStatement).setString(6, city);
        verify(preparedStatement).setString(7, country);
        verify(preparedStatement).setInt(8, id);
        verify(preparedStatement).execute();
    }

    @Test
    public void whenDeleteUser() throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        store.delete(new User(id, new Personality(name, login, email, password),
                date.getTime(), role, city, country));

        verify(preparedStatement).setInt(1, id);
        verify(preparedStatement).execute();
    }

    @Test
    public void whenFindAllUsers() throws SQLException {
        String sql = "SELECT * FROM users;";
        when(connection.createStatement().executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn(id + "");
        when(resultSet.getString("user_name")).thenReturn(name);
        when(resultSet.getString("login")).thenReturn(login);
        when(resultSet.getString("email")).thenReturn(email);
        when(resultSet.getString("password")).thenReturn(password);
        when(resultSet.getDate("createDate")).thenReturn(date);
        when(resultSet.getString("role")).thenReturn(role);
        when(resultSet.getString("city")).thenReturn(city);
        when(resultSet.getString("country")).thenReturn(country);

        List<User> expected = new ArrayList<>();
        expected.add(new User(id, personality, date.getTime(), role, city, country));
        List<User> result = store.findAll();

        assertThat(result, is(expected));
    }

    @Test
    public void whenFindAllElements() throws SQLException {
        String column = "login";
        String query = "SELECT login FROM users;";
        when(connection.createStatement().executeQuery(query)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(column)).thenReturn("test", "test2");

        List<String> result = store.findAllElements(column, query);
        List<String> expected = new ArrayList<>();
        expected.add("test");
        expected.add("test2");

        assertThat(result, is(expected));
    }
}
