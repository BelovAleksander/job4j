package ru.job4j.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class StoreTest {

    @Test
    public void whenAddTwoNewElements() {
        Store store = new Store();
        Store.User user1 = new Store.User(1, "Alex");
        Store.User user2 = new Store.User(2, "Ivan");
        Store.User user3 = new Store.User(3, "Leo");


        List<Store.User> cur = new ArrayList<>();
        cur.add(user1);
        cur.add(user2);
        cur.add(user3);
        List<Store.User> pre = new ArrayList<>();
        pre.add(user1);

        Info info = store.diff(pre, cur);
        assertThat(info.toString(), is(new StringBuilder()
                .append("added: 2 changed: 0 deleted: 0")
                .toString()));
    }

    @Test
    public void whenChangedOneAndDeletedOneAndAddedOne() {
        Store store = new Store();
        Store.User user1 = new Store.User(1, "Alex");
        Store.User user2 = new Store.User(2, "Ivan");
        Store.User user3 = new Store.User(3, "Leo");
        Store.User user4 = new Store.User(4, "Max");
        Store.User user5 = new Store.User(2, "Sveta");

        List<Store.User> pre = new ArrayList<>();
        pre.add(user1);
        pre.add(user2);
        pre.add(user3);
        List<Store.User> cur = new ArrayList<>();
        cur.add(user3);
        cur.add(user4);
        cur.add(user5);
        Info info = store.diff(pre, cur);
        assertThat(info.toString(), is(new StringBuilder()
                .append("added: 1 changed: 1 deleted: 1")
                .toString()));
    }
}
