package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.08.18
 */

public class UserCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        String json = reader.readLine();
        String request = mapper.readValue(json, String.class);
        String response;

        if (request.equals("ready")) {
            ArrayList<String> countries = (ArrayList<String>) ValidateService.getInstance().findCountries();
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(countries);
        } else {
            ArrayList<String> cities = (ArrayList<String>) ValidateService.getInstance().findCities(request);
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cities);
        }
        Writer writer = resp.getWriter();
        writer.write(response);
        writer.flush();
    }
}
