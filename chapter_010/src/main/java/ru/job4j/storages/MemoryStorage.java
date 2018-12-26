package ru.job4j.storages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public class MemoryStorage implements Storage {
    private List<User> users = new ArrayList<>();

    @Override
    public void add(final User user) {
        users.add(user);
    }

    public boolean isEmpty() {
        return this.users.isEmpty();
    }
}
