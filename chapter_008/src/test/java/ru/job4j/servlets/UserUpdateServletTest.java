package ru.job4j.servlets;

import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.08.18
 */

public class UserUpdateServletTest {

    @Test
    public void whenDoPost() throws ServletException, IOException {
        String path = "/WEB-INF/views/update.jsp";
        UserUpdateServlet servlet = new UserUpdateServlet();

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        servlet.doPost(request, response);

        verify(dispatcher).forward(request, response);
    }
}