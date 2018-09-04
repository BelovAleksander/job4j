package ru.job4j.logic;

import ru.job4j.models.Personality;
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
    private final DBStore store = DBStore.getInstance();

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

    public List<String> findCities(String country) {
        String query = String.format("SELECT city FROM users WHERE country = '%s';", country);
        return store.findAllElements("city", query);
    }

    public List<String> findEmails() {
        String query = "SELECT email FROM users;";
        return store.findAllElements("email", query);
    }

    public List<String> findCountries() {
        String query = "SELECT country FROM users;";
        return store.findAllElements("country", query);
    }

    public boolean isValid(final String login, final String password) {
        return store.isValid(login, password);
    }

    public User findByLogin(final String login) {
        return store.findByLogin(login);
    }

    public String inputErrors(final HttpServletRequest request) {
        String error = "";
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        List<String> emails = findEmails();

        if (!action.equals("delete")) {                                     //???
            if (email.equals("")) {
                error = "Email field should be specified!";
            } else if (!password1.equals(password2)) {
                error = "Passwords not equals!";
            }
            if (action.equals("add")) {
                if (emails.contains(email)) {
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
            String city = req.getParameter("city");
            String country = req.getParameter("country");
            Personality personality = new Personality(name, login, email, password);
            this.store.add(personality, role, city, country);
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
            String city = req.getParameter("city");
            String country = req.getParameter("country");

            User user = store.findById(Integer.parseInt(id));
            Personality personality = new Personality(name, login, email, password);
            store.update(user, personality, role, city, country);
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
