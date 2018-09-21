package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.ValidateMC;
import ru.job4j.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MCController extends HttpServlet {
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final ValidateMC VALIDATOR = ValidateMC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/MusicCourt.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        int result = VALIDATOR.executeEntityActions(req);
        if (result != 0) {
            Entity entity = new Entity(result, req.getParameter("name"));
            String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
            System.out.println(json);
            resp.getWriter().append(json).flush();
        }
    }
}
