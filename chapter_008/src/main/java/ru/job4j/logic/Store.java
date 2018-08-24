package ru.job4j.logic;

import ru.job4j.models.User;

import java.util.List;

public interface Store {
    void add(final String name, final String login, final String email,
             final String password, final String role);
    void update(final User user, final String name, final String login,
                final String email, final String password, final String role);
    void delete(final User user);
    User findById(final int id);
    List<User> findAll();
    List<String> findAllEmails();
}
