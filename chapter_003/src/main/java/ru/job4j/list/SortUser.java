package ru.job4j.list;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Содержит различные реализации сортировки list.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 18.06.18
 */
public class SortUser {
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
    public List<User> sortNameLength(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        );
        return list;
    }

    public List<User> sortByAllFields(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        int result = o1.getName().compareTo(o2.getName());
                        return result != 0 ? result : Integer.compare(o1.getAge(), o2.getAge());
                    }
                }
        );
        return list;
    }
}
