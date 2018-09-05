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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.09.18
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class SignInControllerTest {
    private SignInController controller = new SignInController();
    private ValidateService mockService = mock(ValidateService.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);


    @Before
    public void testSingleton() {
        //Tell PowerMock to not to invoke constructor
        suppressConstructor(ValidateService.class);
        mockStatic(ValidateService.class);
    }

    @Test
    public void whenPasswordAndLoginIsValid() throws IOException, ServletException {
        when(ValidateService.getInstance()).thenReturn(mockService);
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("123");
        when(mockService.isValid("admin", "123")).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("chapter_008");

        controller.doPost(request, response);
        verify(response).sendRedirect("chapter_008/");
    }

    @Test
    public void whenPasswordAndLoginInvalid() throws ServletException, IOException {
        when(ValidateService.getInstance()).thenReturn(mockService);
        when(request.getParameter("login")).thenReturn("unregistered");
        when(request.getParameter("password")).thenReturn("123");
        when(mockService.isValid("unregistered", "123")).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("chapter_008");
        when(request.getRequestDispatcher("/WEB-INF/views/signin.jsp")).thenReturn(dispatcher);

        controller.doPost(request, response);
        verify(request).setAttribute("error", "Credential invalid!");
    }
}
