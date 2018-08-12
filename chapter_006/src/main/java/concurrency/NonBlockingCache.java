package ru.job4j.concurrency;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.07.18
 */

@ThreadSafe
public class NonBlockingCache {
    @GuardedBy("this")
    private volatile ConcurrentHashMap<Integer, Base> data = new ConcurrentHashMap<>();

    public void add(Base model) {
        data.put(model.getId(), model);
    }

    public void update(final int id, final int newValue) {
        int old = data.get(id).getVersion();
        data.computeIfPresent(id, new BiFunction<Integer, Base, Base>() {
            @Override
            public Base apply(final Integer integer, Base base) {
                if (old == base.getVersion()) {
                    base.changeValue(newValue);
                    base.incrementVersion();
                } else {
                    throw new OptimisticException("Optimistic Exception!");
                }
                return base;
            }
        });
    }

    public void delete(int id) {
        data.remove(id);
    }

    public Base get(int id) {
        return data.get(id);
    }
}

class Base {
    private final int id;
    private volatile int value;
    @GuardedBy("this")
    private volatile int version;

    public Base(int id, int value) {
        this.id = id;
        this.value = value;
        this.version = 0;
    }

    public int getId() {
        return this.id;
    }
    public int getVersion() {
        return this.version;
    }
    public synchronized void incrementVersion() {
        this.version++;
    }
    public void changeValue(int newValue) {
        this.value = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id
                && value == base.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, value);
    }
}

class OptimisticException extends RuntimeException {
    public OptimisticException(String msg) {
        super(msg);
    }
}
