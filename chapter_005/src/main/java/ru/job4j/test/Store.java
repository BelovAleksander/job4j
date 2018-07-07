package ru.job4j.test;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.07.18
 */

class Store {

    public Info diff(final List<User> previoues, final List<User> current) {
        Info info = new Info();
        Map<Integer, String> map = new HashMap<>();

        for (User old : previoues) {
            map.put(old.id, old.name);
        }
        for (User fresh : current) {
            if (map.containsKey(fresh.id)) {
                if (!fresh.name.equals(map.get(fresh.id))) {
                    info.plusChanged(1);
                }
            } else {
                info.plusNew(1);
            }
        }
        int deleted = previoues.size() - current.size() + info.getNew();
        info.plusDeleted(deleted);
        return info;
    }

    static class User {
        final int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
