package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.09.18
 */
public class ItemController extends HttpServlet {
    private static final ValidateService VALIDATOR = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("action", req.getParameter("action"));
        parameters.put("value", req.getParameter("value"));
        String json = VALIDATOR.execute(parameters);
        System.out.println(json);
        resp.getWriter().append(json).flush();
    }
}
