package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        resp.sendRedirect(String.format("%s/update.jsp?name=%s&login=%s&email=%s&id=%s", req.getContextPath(),
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email"),
                req.getParameter("id")));
    }
}
