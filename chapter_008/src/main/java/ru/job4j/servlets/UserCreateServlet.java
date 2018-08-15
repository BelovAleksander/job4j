package ru.job4j.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.08.18
 */

public class UserCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.sendRedirect(String.format("%s/create.jsp", req.getContextPath()));
    }
}
