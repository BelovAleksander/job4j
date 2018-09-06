package ru.job4j.logic;

import ru.job4j.models.JSONUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UsersStorage {
    private static final UsersStorage INSTANCE = new UsersStorage();
    final private ConcurrentHashMap<String, JSONUser> users = new ConcurrentHashMap<>();

    private UsersStorage() {

    }
    public static UsersStorage getInstance() {
        return INSTANCE;
    }
    public void add(JSONUser user) {
        String id = new StringBuilder().append(user.getFirstName())
                .append(user.getLastName()).toString();
        users.put(id, user);
    }
    public ArrayList<JSONUser> findAll() {
        return new ArrayList<JSONUser>(this.users.values());
    }
    public JSONUser findByNameAndLastName(String name, String lastName) {
        return this.users.get(name + lastName);
    }
}
