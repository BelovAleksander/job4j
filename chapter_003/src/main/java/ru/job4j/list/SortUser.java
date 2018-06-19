package ru.job4j.list;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SortUser {
    public Set<User> sort(List<User> list) {
        TreeSet<User> set = new TreeSet<>();
        set.addAll(list);
        return set;
    }
}
