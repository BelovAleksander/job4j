package ru.job4j.logic;

import ru.job4j.models.Personality;
import ru.job4j.models.User;

import java.util.List;

public interface Store {
    void add(Personality personality, String role, String city, String country);
    void update(User user, Personality personality,
                String role, String city, String country);
    void delete(final User user);
    User findById(final int id);
    List<User> findAll();
    List<String> findAllElements(String column, String query);
}
