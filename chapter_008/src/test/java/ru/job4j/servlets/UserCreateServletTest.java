package ru.job4j.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.logic.ValidateService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserCreateServletTest {
    private UserCreateServlet servlet = new UserCreateServlet();
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);


    @Test
    public void whenDoPost() throws ServletException, IOException {
        String path = "/WEB-INF/views/create.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        servlet.doPost(request, response);

        verify(dispatcher).forward(request, response);
    }
}