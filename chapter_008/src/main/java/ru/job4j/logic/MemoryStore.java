package ru.job4j.logic;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.08.18
 */

@ThreadSafe
public class MemoryStore implements Store {
    @GuardedBy("this")
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private int countForId = 0;

    private MemoryStore() {
    }
    private static class StoreHolder {
        private final static MemoryStore INSTANCE = new MemoryStore();
    }

    public static MemoryStore getInstance() {
        return StoreHolder.INSTANCE;
    }

    public synchronized void add(final String name, final String login, final String email,
                                 final String password, final String role) {
        this.users.put(email, new User(
                this.countForId, name, login, email, password, System.currentTimeMillis(), role));
        countForId++;
    }

    public void update(final User user, final String name, final String login,
                       final String email, final String password, final String role) {
        if (!name.equals("")) {
            user.setName(name);
        }
        if (!login.equals("")) {
            user.setLogin(login);
        }
        if (!email.equals("")) {
            this.users.remove(user.getEmail());
            user.setEmail(email);
            this.users.put(email, user);
        }
    }

    public void delete(final User user) {
        this.users.remove(user.getEmail());
    }

    public User findById(final int id) {
        User result = null;
        for (User user : this.users.values()) {
            if (user.getId() == id) {
                result = user;
                break;
            }
        }
        return result;
    }

    public List<User> findAll() {
        List<User> res = new ArrayList<>();
        res.addAll(this.users.values());
        return res;
    }

    public List<String> findAllEmails() {
        List<String> res = new ArrayList<>();
        res.addAll(this.users.keySet());
        return res;
    }
}
