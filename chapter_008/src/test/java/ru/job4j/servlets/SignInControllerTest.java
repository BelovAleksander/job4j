package ru.job4j.servlets;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.logic.DBStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.08.18
 */

public class SignInControllerTest {
    BasicDataSource source;

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

        Connection conn = source.getConnection();
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
    public void whenPasswordAndLoginIsValid() throws IOException, ServletException {
        SignInController controller = new SignInController();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("chapter_008");

        controller.doPost(request, response);

        verify(response).sendRedirect("chapter_008/");
    }

    @Test
    public void whenPasswordAndLoginInvalid() throws ServletException, IOException {
        SignInController controller = new SignInController();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("chapter_008");
        when(request.getRequestDispatcher("/WEB-INF/views/signin.jsp")).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Credential invalid!");
    }
}