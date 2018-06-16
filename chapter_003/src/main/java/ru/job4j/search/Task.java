package ru.job4j.search;

/**
 * Форма под задачу.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.06.18
 */

public class Task {
    private final String desc;
    private final int priority;

    public Task(final String desc, final int priority) {
        this.desc = desc;
        this.priority = priority;
    }

    public final String getDesc() {
        return this.desc;
    }

    public final int getPriority() {
        return this.priority;
    }
}
