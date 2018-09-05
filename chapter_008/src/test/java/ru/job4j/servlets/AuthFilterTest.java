package ru.job4j.servlets;

import org.junit.Test;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.09.18
 */
public class AuthFilterTest {
    private AuthFilter filter = new AuthFilter();
    private HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    private HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    private FilterChain mockChain = mock(FilterChain.class);
    private HttpSession mockSession = mock(HttpSession.class);

    @Test
    public void whenSignIn() throws IOException, ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/signin");
        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void whenUnregisteredUser() throws IOException, ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/create");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("login")).thenReturn(null);
        when(mockRequest.getContextPath()).thenReturn("chapter_008");
        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockResponse).sendRedirect("chapter_008/signin");
    }

    @Test
    public void whenRegisteredUser() throws IOException, ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/create");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("login")).thenReturn("user");
        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain).doFilter(mockRequest, mockResponse);
    }
}