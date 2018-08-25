package ru.job4j.servlets;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserCreateServletTest {

    @Test
    public void whenDoPost() throws ServletException, IOException {
        String path = "/WEB-INF/views/create.jsp";
        UserCreateServlet servlet = new UserCreateServlet();

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        servlet.doPost(request, response);

        verify(dispatcher).forward(request, response);
    }
}