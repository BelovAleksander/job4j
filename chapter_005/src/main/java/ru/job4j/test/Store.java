package ru.job4j.test;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.07.18
 */

class Store {

    public Info diff(final List<User> previoues, final List<User> current) {
        Info info = new Info();
        Map<Integer, Queue<String>> map = new HashMap<>();

        for (User old : previoues) {
            Queue<String> data = new LinkedList<>();
            data.offer(old.name);
            map.put(old.id, data);
        }
        for (User fresh : current) {
            if (map.containsKey(fresh.id)) {
                Queue<String> data = map.get(fresh.id);
                data.offer(fresh.name);
                map.put(fresh.id, data);
            } else {
                Queue<String> data = new LinkedList<>();
                data.offer(null);
                data.offer(fresh.name);
                map.put(fresh.id, data);
            }
        }
        for (Integer id : map.keySet()) {
            Queue<String> data = map.get(id);
            String old = data.poll();
            String fresh = data.peek();
            if (old == null) {
                info.plusNew();
            } else if (fresh == null) {
                info.plusDeleted();
            } else if (!old.equals(fresh)) {
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
