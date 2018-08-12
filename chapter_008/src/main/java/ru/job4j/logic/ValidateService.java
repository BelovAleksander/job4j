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
    private final HashMap<String, Function<String, Boolean>> actions = new HashMap<>();
    private HttpServletRequest req;
    private final MemoryStore store = MemoryStore.getInstance();

    private ValidateService(){
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
            boolean result = true;
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");

            if ((name == null) || (email == null)) {
                result = false;
                throw new DataInputException("Name and email fields should be specified!");
            } else if (store.findAllEmails().contains(email)) {
                throw new DataInputException("User with this email already exist!");
            } else if (login == null) {
                login = email;
            }
            if (result) {
                this.store.add(name, login, email);
            }
            return result;
        };
    }

    public Function<String, Boolean> update() {
        return action -> {
            boolean result = false;
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");

            if (id == null) {
                throw new DataInputException("Please, input id");
            }
            User user = store.findById(Integer.parseInt(id));
            if (user != null) {
                store.update(user, name, login, email);
                result = true;
            }
            return result;
        };
    }

    public Function<String, Boolean> delete() {
        return action -> {
            boolean result = false;
            String id = req.getParameter("id");
            if (id == null) {
                throw new DataInputException("Please, input id!");
            }
            User user = this.store.findById(Integer.parseInt(id));
            if (user == null) {
                throw new DataInputException("Such user doesn't exist!");
            }
            store.delete(user);
            result = true;
            return result;
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
