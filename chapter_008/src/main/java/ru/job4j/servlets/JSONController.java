package ru.job4j.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.logic.UsersStorage;
import ru.job4j.models.JSONUser;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.09.18
 */
public class JSONController extends HttpServlet {
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        JSONUser user = CONVERTER.readValue(req.getReader(), JSONUser.class);
        System.out.println(user.toString());
        boolean result = UsersStorage.getInstance().add(user);
        System.out.println(result);
        if (result) {
            String json = CONVERTER.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            PrintWriter writer = resp.getWriter();
            writer.append(json);
            writer.flush();
        }
    }
}
