package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.UsersStorage;
import ru.job4j.models.JSONUser;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 29.08.18
 */
public class JSONController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = req.getReader();

        String line = reader.readLine();
        sb.append(line);
        JSONUser user = mapper.readValue(sb.toString(), JSONUser.class);
        UsersStorage.getInstance().add(user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("users", UsersStorage.getInstance().findAll());
    }
}
