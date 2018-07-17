package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 13.07.2018
 * Класс является реализацией простой блокирующей очереди.
 * @param <T> наследник Numbers
 */

@ThreadSafe
public class SimpleBlockingQueue<T extends Integer> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final static int QUEUE_MAX_SIZE = 2;

    public synchronized void offer(final T value) {
        if (queue.size() == QUEUE_MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        queue.offer(value);
    }
    public synchronized T poll() {
        if (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        return queue.poll();
    }
}

class Interaction<T extends Integer> {

    private SimpleBlockingQueue queue = new SimpleBlockingQueue<>();

    class Consumer<T extends Integer> implements Runnable {
        private final Integer[] array;
        private int count;

        public Consumer(final int count) {
            this.count = count;
            this.array = new Integer[count];
        }

        public T[] getValues() {
            return (T[]) array;
        }

        @Override
        public void run() {
            while (count > 0) {
                array[5 - count] = queue.poll();
                count--;
            }
        }
    }

    class Producer implements Runnable {
        private final int value;
        private int count;

        public Producer(final int value, final int count) {
            this.value = value;
            this.count = count;
        }

        @Override
        public void run() {
            while (count > 0) {
                queue.offer(value);
                count--;
            }
        }
    }
}
