package ru.job4j.servlets;

import org.junit.Before;
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

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.09.18
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UsersControllerTest {
    private UsersController controller = new UsersController();
    private ValidateService serviceMock = mock(ValidateService.class);
    private RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
    private HttpServletRequest requestMock = mock(HttpServletRequest.class);
    private HttpServletResponse responseMock = mock(HttpServletResponse.class);

    @Before
    public void testSingleton() {
        suppressConstructor(ValidateService.class);
        mockStatic(ValidateService.class);
    }

    @Test
    public void whenDoGet() throws IOException, ServletException {
        when(ValidateService.getInstance()).thenReturn(serviceMock);
        when(requestMock.getRequestDispatcher("/WEB-INF/views/list.jsp")).thenReturn(dispatcherMock);
        when(serviceMock.findAll()).thenReturn(null);
        controller.doGet(requestMock, responseMock);

        verify(responseMock).setContentType("text/html");
        verify(requestMock).setAttribute("users", serviceMock.findAll());
        verify(dispatcherMock).forward(requestMock, responseMock);
    }
    @Test
    public void whenErrorsErrorsEveryWhere() throws ServletException, IOException {
        when(ValidateService.getInstance()).thenReturn(serviceMock);
        when(serviceMock.inputErrors(requestMock)).thenReturn("someError");
        when(requestMock.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(dispatcherMock);
        controller.doPost(requestMock, responseMock);

        verify(requestMock).setAttribute("error", "someError");
        verify(dispatcherMock).forward(requestMock, responseMock);
        verify(responseMock).setContentType("text/html");
    }

    @Test
    public void whenClean() throws IOException, ServletException {
        when(ValidateService.getInstance()).thenReturn(serviceMock);
        when(serviceMock.inputErrors(requestMock)).thenReturn("");
        when(requestMock.getParameter("action")).thenReturn("someAction");
        when(requestMock.getContextPath()).thenReturn("somePath");
        controller.doPost(requestMock, responseMock);

        verify(responseMock).setContentType("text/html");
        verify(serviceMock).init(requestMock);
        verify(serviceMock).applyFunc("someAction");
        verify(responseMock).sendRedirect("somePath/");
    }
}
