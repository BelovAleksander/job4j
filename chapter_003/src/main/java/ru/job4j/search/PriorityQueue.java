package ru.job4j.search;

import java.util.LinkedList;

/**
 * Формирует очередь.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.06.18
 */

public class PriorityQueue {
    public final LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     * @param task задача
     */
    public final void put(final Task task) {
        if (this.tasks.isEmpty()) {
            this.tasks.add(task);
        } else {
            for (int index = 0; index < this.tasks.size(); index++) {
                if (tasks.get(index).getPriority() > task.getPriority()) {
                    tasks.add(index, task);
                    break;
                }
            }
        }
    }

    public final Task take() {
        return this.tasks.poll();
    }
}
