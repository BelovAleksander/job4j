package ru.job4j.servlets;

import org.apache.log4j.Logger;
import ru.job4j.items.logic.ItemValidator;

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
    private static final ItemValidator VALIDATOR = ItemValidator.getInstance();
    private static final Logger LOG = Logger.getLogger("APP2");

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("action", req.getParameter("action"));
        parameters.put("value", req.getParameter("value"));
        String json = VALIDATOR.execute(parameters);
        resp.getWriter().append(json).flush();
    }
}
