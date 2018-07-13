package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 13.07.2018
 * Класс является реализацией простой блокирующей очереди.
 * @param <T> любой тип
 */

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final static int QUEUE_MAX_SIZE = 2;

    public synchronized void offer(final T value) {
        if (Thread.currentThread().getName().equals("producer")
                && queue.size() == QUEUE_MAX_SIZE) {
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
        if (Thread.currentThread().getName().equals("consumer")
                    && queue.size() == 0) {
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

class Interaction {

    private SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

    class Consumer<T> implements Runnable {
        private final Object[] array;
        private int count;

        public Consumer(final int count) {
            this.count = count;
            this.array = new Object[count];
        }

        public T[] getValues() {
            return (T[]) array;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("consumer");
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
            Thread.currentThread().setName("producer");
            while (count > 0) {
                queue.offer(value);
                count--;
            }
        }
    }
}
