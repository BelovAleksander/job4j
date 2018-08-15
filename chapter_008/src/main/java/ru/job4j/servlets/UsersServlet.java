package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

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

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValidateService service = ValidateService.getInstance();
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder("<table>");
        for (User user : service.findAll()) {
            sb.append("<tr><td>").append(user.toString())
                    .append("<form action='").append(req.getContextPath()).append("/edit', method='GET'>")
                    .append("<input type='hidden', name='id', value='").append(user.getId()).append("'/>")
                    .append("<input type='hidden', name='name', value='").append(user.getName()).append("'/>")
                    .append("<input type='hidden', name='login', value='").append(user.getLogin()).append("'/>")
                    .append("<input type='hidden', name='email', value='").append(user.getEmail()).append("'/>")
                    .append("<input type='submit', value='edit', name='edit'/>")
                    .append("</form>")
                    .append("<form action='").append(req.getContextPath()).append("/user', method='POST'>")
                    .append("<input type='hidden', name='id', value='").append(user.getId()).append("'/>")
                    .append("<input type='hidden', name='action', value='delete'/>")
                    .append("<input type='submit', name='delete', value='delete'/>")
                    .append("</form>")
                    .append("</td></tr>");
        }
        sb.append("</table>");
        writer.append("<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>Title</title>"
                + "</head>"
                + "<body>"
                + "<h1>Users table:</h1>"
                + "<form action='" + req.getContextPath() + "/create', method='GET'>"
                + "<input type='submit', name='create', value='create'>"
                + "</form>"
                + sb.toString()
                + "</body>"
                + "</html>");
        writer.flush();
    }
}
