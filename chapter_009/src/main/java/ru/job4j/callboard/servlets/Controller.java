package ru.job4j.callboard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ru.job4j.callboard.logic.CallBoardValidation;
import ru.job4j.callboard.models.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 29.10.18
 */

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final ConcurrentHashMap<String, Function<HttpServletRequest, String>> ACTIONS =
            new ConcurrentHashMap<>();
    private static final Logger LOG = Logger.getLogger("APP2");
    private static final DiskFileItemFactory FACTORY = new DiskFileItemFactory();
    private static final ObjectMapper CONVERTER = new ObjectMapper();
    private static final CallBoardValidation VALIDATOR = CallBoardValidation.getInstance();

    @Override
    public void init() {
        ACTIONS.put("addPhoto", addPhoto());
        ACTIONS.put("deletePhoto", deletePhoto());
        ACTIONS.put("addUser", addUser());
        ACTIONS.put("addAdvert", executePrimitiveAction());
        ACTIONS.put("updateAdvert", executePrimitiveAction());
        ACTIONS.put("signIn", signIn());
        ACTIONS.put("singOut", signOut());
        ACTIONS.put("getBaseUrl", getBaseUrl());
        ACTIONS.put("deleteAdvert", executePrimitiveAction());
        ACTIONS.put("changeStatus", executePrimitiveAction());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        LOG.info("servlet | doGet");
        String action = req.getParameter("action");
        String value = req.getParameter("value");
        String response = VALIDATOR.execute(
                createCommand(action, value)
        );
        PrintWriter writer = resp.getWriter();
        LOG.info("response: " + response);
        writer.append(response);
        writer.flush();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        final String action = req.getParameter("action");
        String response;
        if (action == null) {
            response = execute("addPhoto", req);
        } else {
            response = execute(action, req);       //todo: refactor
        }
        LOG.info("response: " + response);
        if (response == null) {
            req.getRequestDispatcher("/WEB-INF/views/Home.jsp").forward(req, resp);
        } else {
            final PrintWriter writer = resp.getWriter();
            writer.append(response);
            writer.flush();
        }
    }

    /**
     * logically, this method should be processing by GET-type query but it requires pre-treatment like
     * POST-type queries, so something like this... This method exists because "${pageContext.servletContext.contextPath}"
     * return "chapter_009/..." instead of "localhost:8080/chapter_009/...". In other words, this method return the host.
     * @return baseURL
     */
    public Function<HttpServletRequest, String> getBaseUrl() {
        return req -> {
            LOG.info("***servlet | getUrl");
            final String url = req.getRequestURL().toString();
            LOG.info("URL: " + url);
            return url.substring(0, url.length() - req.getRequestURI().length())
                    + req.getContextPath() + "/";
        };
    }

    private Function<HttpServletRequest, String> addPhoto() {
        return req -> {
            LOG.info("***servlet | addPhoto***");
            final ServletContext servletContext = this.getServletConfig().getServletContext();
            final File repository = (File) servletContext.getAttribute(servletContext.TEMPDIR);
            FACTORY.setRepository(repository);
            final ServletFileUpload upload = new ServletFileUpload(FACTORY);
            String path = null;
            try {
                final List<FileItem> items = upload.parseRequest(req);
                final Iterator<FileItem> it = items.iterator();
                while (it.hasNext()) {
                    final FileItem item = it.next();
                    if (!item.isFormField()) {
                        path = System.getProperty("user.dir") + "/images/" + item.getName();
                        LOG.info(path);
                        final File uploadedFile = new File(path);
                        item.write(uploadedFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return path;
        };
    }

    private Function<HttpServletRequest, String> deletePhoto() {
        return req -> {
            LOG.info("***servlet | deletePhoto***");
            String path = VALIDATOR.execute(createCommand("getPhoto", req.getParameter("value")));
            File photo = new File(path);
            photo.delete();
            return null;
        };
    }

    private Function<HttpServletRequest, String> addUser() {
        return req -> {
            LOG.info("***servlet | addUser***");
            signIn().apply(req);
            final String userId = executePrimitiveAction().apply(req);
            req.getSession().setAttribute("userId", userId);
            return null;
        };
    }

    private Function<HttpServletRequest, String> signIn() {
        return req -> {
            LOG.info("***servlet | signIn***");
            try {
                User user = CONVERTER.readValue(req.getParameter("value"), User.class);
                LOG.info(user.toString());
                final HttpSession session = req.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("password", user.getPassword());
                session.setAttribute("role", user.getRole().getName());
                session.setAttribute("userId", user.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private Function<HttpServletRequest, String> signOut() {
        return req -> {
            HttpSession session = req.getSession();
            session.invalidate();
            return null;
        };
    }

    /**
     * method execute actions that don't requires pre-treatment
     * @return null
     */
    private Function<HttpServletRequest, String> executePrimitiveAction() {
        return req -> {
            LOG.info("***servlet | primitiveAction***");
            return VALIDATOR.execute(createCommand(req.getParameter("action"), req.getParameter("value")));
        };
    }

    private HashMap<String, String> createCommand(final String action, final String value) {
        final HashMap<String, String> result = new HashMap<>();
        result.put("action", action);
        result.put("value", value);
        return result;
    }

    private String execute(final String action, final HttpServletRequest req) {
        LOG.info("***servlet | execute***");
        LOG.info("action: " + action);
        return ACTIONS.get(action).apply(req);
    }
}

