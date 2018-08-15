package ru.job4j.logic;

import ru.job4j.models.User;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.08.18
 */

public class ValidateService {
    private final static ValidateService INSTANCE = new ValidateService();
    private HashMap<String, Function<String, Boolean>> actions = new HashMap<>();
    private HttpServletRequest req;
    private final MemoryStore store = MemoryStore.getInstance();

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

    public Function<String, Boolean> add() {
        return action -> {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");

            if ((name.equals("")) || (email.equals(""))) {
                throw new DataInputException("Name and email fields should be specified!");
            } else if (store.findAllEmails().contains(email)) {
                throw new DataInputException("User with this email already exist!");
            } else if (login.equals("")) {
                login = email;
            }
            this.store.add(name, login, email);
            return true;
        };
    }

    public Function<String, Boolean> update() {
        return action -> {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");

            User user = store.findById(Integer.parseInt(id));
            store.update(user, name, login, email);
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
