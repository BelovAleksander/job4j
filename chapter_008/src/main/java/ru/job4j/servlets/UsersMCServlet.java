package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.ValidateMC;
import ru.job4j.models.UserMC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersMCServlet extends HttpServlet {
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final ValidateMC VALIDATOR = ValidateMC.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        int result = VALIDATOR.executeUserActions(req);
        System.out.println(result);
        if (result != 0) {
            UserMC user = CONVERTER.readValue(req.getParameter("user"), UserMC.class);
            user.setId(result);
            String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            System.out.println(json);
            resp.getWriter().append(json).flush();
        } else {
            req.getRequestDispatcher("MusicCourt.jsp").forward(req, resp);
        }
    }
}
