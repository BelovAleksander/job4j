package ru.job4j.logic;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.models.Personality;
import ru.job4j.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 11.08.18
 */

@ThreadSafe
public class MemoryStore  {
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
                                 final String password, final String role, final String city, final String country) {
        this.users.put(email, new User(
                this.countForId, new Personality(name, login, email, password), System.currentTimeMillis(), role, city, country));
        countForId++;
    }

    public void update(final User user, final String name, final String login,
                       final String email, final String password, final String role) {
        Personality personality = new Personality(name, login, email, password);
        user.setPersonality(personality);
        user.setRole(role);
    }

    public void delete(final User user) {
        this.users.remove(user.getPersonality().getEmail());
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
