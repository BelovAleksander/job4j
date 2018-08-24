package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.08.18
 */

public class UsersController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        req.setAttribute("users", ValidateService.getInstance().findAll());
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        ValidateService service = ValidateService.getInstance();
        String error = service.inputErrors(req);
        if (!error.equals("")) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
        } else {
            service.init(req);
            service.apply(req.getParameter("action"));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
