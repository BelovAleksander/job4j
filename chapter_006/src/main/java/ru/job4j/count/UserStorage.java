package ru.job4j.count;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 12.07.18
 */

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    public HashSet<User> data = new HashSet<>();

    public synchronized boolean add(User user) {
        return data.add(user);
    }
    public synchronized boolean update(int id, User user) {
        boolean result = false;
        User del = null;
        for (User el : this.data) {
            if (el.getId() == id) {
                del = el;
                result = true;
                break;
            }
        }
        if (result) {
            this.data.remove(del);
            this.data.add(user);
        }
        return result;
    }
    public synchronized boolean delete(User user) {
        return data.remove(user);
    }
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User sender = null;
        User recipient = null;
        for (User el : data) {
            if (el.getId() == fromId) {
                sender = el;
            } else if (el.getId() == toId) {
                recipient = el;
            }
        }
        if (recipient != null && sender != null) {
            sender.changeAmount(-amount);
            recipient.changeAmount(amount);
            result = true;
        }
        return result;
    }
    public synchronized User getUser(int id) {
        User result = null;
        for (User el : this.data) {
            if (el.getId() == id) {
                result = el;
                break;
            }
        }
        return result;
    }
}
