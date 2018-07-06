package ru.job4j.test;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.07.18
 */

class Store {

    public Info diff(final List<User> previoues, final List<User> current) {
        Info info = new Info();
        Map<Integer, String[]> map = new HashMap<>();

        for (User old : previoues) {
            String[] array = new String[2];
            array[0] = old.name;
            map.put(old.id, array);
        }
        for (User fresh : current) {
            if (map.containsKey(fresh.id)) {
                String[] array = map.get(fresh.id);
                array[1] = fresh.name;
                map.put(fresh.id, array);
            } else {
                String[] array = new String[2];
                map.put(fresh.id, array);
            }
        }
        for (Integer id : map.keySet()) {
            String[] array = map.get(id);
            if (array[0] == null) {
                info.plusNew();
            } else if (array[1] == null) {
                info.plusDeleted();
            } else if (!array[0].equals(array[1])) {
                info.plusChanged();
            }
        }
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
