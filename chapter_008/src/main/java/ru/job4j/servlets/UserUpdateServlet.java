package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.08.18
 */

public class UserUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ValidateService service = ValidateService.getInstance();
        service.init(req);
        service.apply("update");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>Title</title>"
                + "</head>"
                + "<body>"
                + "<form action='" + req.getContextPath() + "/user', method=POST>"
                + "name: <input type='text', name='name', value='" + req.getParameter("name") + "'/>"
                + "login: <input type='text', name='login', value='" + req.getParameter("login") + "'/>"
                + "email: <input type='email', name='email', value='" + req.getParameter("email") + "'/>"
                + "<input type='hidden', name='id', value='" + req.getParameter("id") + "'/>"
                + "<input type='hidden', name='action', value='update'/>"
                + "<input type='submit', name='edit'/>"
                + "</form>"
                + "</body>"
                + "</html>");
        writer.flush();
    }
}
