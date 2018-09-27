package ru.job4j.servlets;

import ru.job4j.logic.ValidateMC;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProviderServlet extends HttpServlet {
    private static final ValidateMC VALIDATOR = ValidateMC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String json = VALIDATOR.getJson(req);
        resp.getWriter().append(json).flush();
    }
}
