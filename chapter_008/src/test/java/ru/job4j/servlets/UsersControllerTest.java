package ru.job4j.servlets;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.logic.DBStore;
import ru.job4j.logic.ValidateService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.08.18
 */

public class UsersControllerTest {
    private BasicDataSource source;

    @Before
    public void initDB() throws SQLException {
        this.source = new BasicDataSource();

        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost/servlets_db");
        source.setUsername("postgres");
        source.setPassword("password");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);

        DBStore.getInstance().setSource(source);

        Connection conn = this.source.getConnection();
        String sql =
                "INSERT INTO users(id, user_name, login, email, password, createDate, role)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, 0);
        ps.setString(2, "alexander");
        ps.setString(3, "admin");
        ps.setString(4, "example@mail.com");
        ps.setString(5, "123");
        ps.setDate(6, new Date(System.currentTimeMillis()));
        ps.setString(7, "admin");
        ps.execute();
    }

    @After
    public void cleanDB() throws SQLException {
        Connection conn = source.getConnection();
        conn.createStatement().executeUpdate("DELETE FROM users;");
        conn.close();
    }

    @Test
    public void whenDoGet() throws IOException, ServletException, SQLException {
        final String path = "/WEB-INF/views/list.jsp";
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final UsersController controller = new UsersController();

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
    @Test
    public void whenEmailIsEmpty() {
        final HttpServletRequest request = mock(HttpServletRequest.class);

        ValidateService service = ValidateService.getInstance();
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("email")).thenReturn("");
        String error = service.inputErrors(request);

        assertThat(error.equals("Email field should be specified!"), is(true));
    }

    @Test
    public void whenPasswordsDoesntEquals() {
        final HttpServletRequest request = mock(HttpServletRequest.class);

        ValidateService service = ValidateService.getInstance();
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("email")).thenReturn("example2@mail.com");
        when(request.getParameter("password1")).thenReturn("1");
        when(request.getParameter("password2")).thenReturn("2");
        String error = service.inputErrors(request);

        assertThat(error.equals("Passwords not equals!"), is(true));
    }

    @Test
    public void whenSuchEmailAlreadyExists() throws SQLException {
        final HttpServletRequest request = mock(HttpServletRequest.class);

        ValidateService service = ValidateService.getInstance();
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("password1")).thenReturn("1");
        when(request.getParameter("password2")).thenReturn("1");
        when(request.getParameter("email")).thenReturn("example@mail.com");


        String error = service.inputErrors(request);
        assertThat(error.equals("User with this email already exist"), is(true));
    }
}
