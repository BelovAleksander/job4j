package ru.job4j.count;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;


@ThreadSafe
public class User {
    private final int id;
    @GuardedBy("this")
    private int amount;

    public User(final int id, final int amount) {
        this.id = id;
        this.amount = amount;
    }
    public synchronized void changeAmount(int value) {
        this.amount += value;
    }
    public synchronized int getId() {
        return this.id;
    }
    public synchronized int getAmount() {
        return this.amount;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public synchronized int hashCode() {

        return Objects.hash(id);
    }
}
