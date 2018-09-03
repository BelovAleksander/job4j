package ru.job4j.logic;

import ru.job4j.models.User;

import java.util.List;

public interface Store {
    void add(String name, String login, String email, String password, String role, String city, String country);
    void update(User user, String name, String login, String email, String password,
                String role, String city, String country);
    void delete(final User user);
    User findById(final int id);
    List<User> findAll();
    List<String> findAllElements(String column, String query);
}
