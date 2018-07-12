package ru.job4j.count;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserStorageTest {
    private UserStorage storage = new UserStorage();

    class AddUser implements Runnable {
        private User user;

        public AddUser(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            storage.add(user);
        }
    }
    @Test
    public void whenAddAndUpdateAndDelete() {
        User user1 = new User(1, 100);
        User user2 = new User(1, 200);

        Thread add = new Thread(new AddUser(user1));
        Thread main = Thread.currentThread();
        add.start();
        try {
            add.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        storage.update(1, user2);
        assertThat(storage.getUser(1).getAmount(), is(200));
    }

    @Test
    public void whenDeleteThenDataDoestContainsIt() {
        User user = new User(1, 100);
        storage.add(user);
        storage.delete(user);
        assertThat(storage.data.contains(user), is(false));
    }

    @Test
    public void whenAddUpdateDeleteThenReturnTrue() {
        User user1 = new User(1, 100);
        User user2 = new User(1, 200);
        boolean resAdd = storage.add(user1);
        boolean resUpdate = storage.update(1, user2);
        boolean resDelete = storage.delete(user2);
        assertThat(resAdd && resUpdate && resDelete, is(true));
    }
}
