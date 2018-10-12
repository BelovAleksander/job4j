package ru.job4j.servlets;

import ru.job4j.cars.logic.CarValidator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Logger;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 05.10.18
 */
public class CarController extends HttpServlet {
    private static final Logger LOG = Logger.getLogger("APP2");
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        final CarValidator validator = CarValidator.getInstance();
        final HashMap<String, String> param = new HashMap<>();
        param.put("action", req.getParameter("action"));
        param.put("value", req.getParameter("value"));
        String result = validator.execute(param);
        PrintWriter writer = resp.getWriter();
        writer.append(result);
        writer.flush();
    }
}
