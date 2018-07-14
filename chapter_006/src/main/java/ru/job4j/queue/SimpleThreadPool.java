package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 14.07.18
 * Класс реализует простой вариант пула нитей.
 */

@ThreadSafe
public class SimpleThreadPool {
    @GuardedBy("this")
    private final List<Thread> threads;
    @GuardedBy("this")
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>(3);

    public SimpleThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.threads = new LinkedList<>();
        while (size > 0) {
            threads.add(new InnerThread());
            size--;
        }
        for (Thread th : threads) {
            th.start();
        }
    }

    public void work(Runnable job) {
        synchronized (tasks) {
            tasks.add(job);
            tasks.notify();
        }
    }

    public synchronized void shutdown() {
        System.out.println("shutdown");
        for (Thread th : threads) {
            th.interrupt();

        }
    }

    /**
     * После вызова метода shutdown() потоки будто продолжают работать.
     * Как дебажить такой код?
     */
    class InnerThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (tasks) {
                    while (tasks.isEmpty() && !Thread.currentThread().isInterrupted()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted in InnerThread");
                        }
                    }
                    task = tasks.poll();
                    task.run();
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleThreadPool pool = new SimpleThreadPool();
        pool.work(new Thread() {
            public void run() {
                System.out.println("first job");
            }
        });
        pool.work(new Thread() {
            public void run() {
                System.out.println("second job");
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        /**
         * Рассчет был на то, что эта строка никогда не выведется, потому что
         * исполняющие строки должны завершиться методом shutdown().
         * Почему процесс не завершается? Если все 4 строки (при size = 4) отработали -
         * кто обрабатывает новую задачу?
         */
        pool.work(new Thread() {
            public void run() {
                System.out.println("this message should't be printed");
            }
        });
    }
}
