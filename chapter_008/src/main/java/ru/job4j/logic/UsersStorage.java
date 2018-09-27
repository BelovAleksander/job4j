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

    public boolean add(JSONUser user) {
        boolean result = false;
        String id = new StringBuilder()
                .append(user.getFirstName())
                .append(user.getLastName()).toString();
        if (!users.keySet().contains(id)) {
            result = true;
            users.put(id, user);
        }
        return result;
    }
}
