package ru.job4j.logic;

import ru.job4j.models.User;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.08.18
 */

public class ValidateService {
    private final static ValidateService INSTANCE = new ValidateService();
    private final HashMap<String, Function<String, Boolean>> actions = new HashMap<>();
    private HttpServletRequest req;
    private final Store store = DBStore.getInstance();

    private ValidateService() {
        }
    public static ValidateService getInstance() {
        return INSTANCE;
    }

    public void init(final HttpServletRequest req) {
        this.req = req;
        this.actions.put("add", add());
        this.actions.put("update", update());
        this.actions.put("delete", delete());
    }

    public boolean isValid(final String login, final String password) {
        return DBStore.isValid(login, password);
    }

    public User findByLogin(final String login) {
        return DBStore.findByLogin(login);
    }

    public String inputErrors(final HttpServletRequest request) {
        String error = "";
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        if (!action.equals("delete")) {                                     //???
            if (email.equals("")) {
                error = "Email field should be specified!";
            } else if (!password1.equals(password2)) {
                error = "Passwords not equals!";
            }
            if (action.equals("add")) {
                if (store.findAllEmails().contains(email)) {
                    error = "User with this email already exist";
                }
            }
        }
        return error;
    }

    public Function<String, Boolean> add() {
        return action -> {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String password = req.getParameter("password1");
            String role = req.getParameter("role");
            if (login.equals("")) {
                login = email;
            }
            this.store.add(name, login, email, password, role);
            return true;
        };
    }

    public Function<String, Boolean> update() {
        return action -> {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String password = req.getParameter("password1");
            String role = req.getParameter("role");

            User user = store.findById(Integer.parseInt(id));
            store.update(user, name, login, email, password, role);
            return true;
        };
    }

    public Function<String, Boolean> delete() {
        return action -> {
            String id = req.getParameter("id");
            User user = findById(Integer.parseInt(id));
            store.delete(user);
            return true;
        };
    }

    public List<User> findAll() {
        return this.store.findAll();
    }

    public User findById(int id) {
        return this.store.findById(id);
    }

    public boolean apply(final String action) {
        return this.actions.get(action).apply(req.getParameter("action"));
    }
}
