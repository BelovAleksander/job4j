package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.07.18
 * Класс реализует простой вариант пула нитей.
 */

@ThreadSafe
public class SimpleThreadPool {
    @GuardedBy("this")
    private final Thread[]threads;
    @GuardedBy("this")
    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>(5);

    private boolean shutdown = false;

    public SimpleThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new InnerThread();
            threads[i].start();
        }
    }

    public void work(Runnable job) {
        try {
            tasks.put(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void shutdown() {
        this.shutdown = true;
        for (Thread t : threads) {
            t.interrupt();
            System.out.println(t + "shutdowned");
        }
    }

    class InnerThread extends Thread {
        @Override
        public void run() {
            Runnable task = null;
            while (!shutdown) {
                try {
                    task = tasks.take();
                } catch (InterruptedException e) {
                    System.out.println("shout down");
                }
                if (task != null) {
                    task.run();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.work(new Thread() {
                public void run() {
                    try {
                        System.out.println(" job " + finalI);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("job off " + finalI);
                    }
                }
            });
        }

        pool.shutdown();
        pool.work(new Thread() {
            public void run() {
                System.out.println("this message should't be printed");
            }
        });
    }
}
