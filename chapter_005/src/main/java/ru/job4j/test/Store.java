package ru.job4j.test;

import java.util.List;
import java.util.Objects;

class Store {

    public Info diff(final List<User> previoues, final List<User> current) {
        Info info = new Info();
        for (User old : previoues) {
            info.plusDeleted();
            for (User fresh : current) {
                if (old.equals(fresh)) {
                    info.minusDeleted();
                    break;
                } else if (old.id == fresh.id) {
                    info.plusChanged();
                    info.minusDeleted();
                }
            }
        }
        int diff = current.size() - previoues.size() + info.getDeleted();
        while (diff != 0) {
            info.plusNew();
            diff--;
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

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id
                    && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
