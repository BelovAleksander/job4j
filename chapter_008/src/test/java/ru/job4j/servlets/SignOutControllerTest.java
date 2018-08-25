package ru.job4j.servlets;

import org.junit.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 25.08.18
 */
public class SignOutControllerTest {
    @Test
    public void whenSignOut() throws ServletException, IOException {
        SignOutController controller = new SignOutController();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session =  mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        controller.doGet(request, response);
        verify(request.getSession()).invalidate();
    }
}
