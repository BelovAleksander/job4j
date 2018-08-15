package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.08.18
 */

public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ValidateService service = ValidateService.getInstance();
        service.init(req);
        service.apply(req.getParameter("action"));
        resp.sendRedirect(req.getContextPath() + "/list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ValidateService service = ValidateService.getInstance();
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("Users:\n");
        for (User user : service.findAll()) {
            writer.append(user.toString()).append("\n");
        }
        writer.flush();
    }
}
