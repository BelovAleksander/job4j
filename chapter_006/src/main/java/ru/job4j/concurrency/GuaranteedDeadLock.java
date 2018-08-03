package ru.job4j.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * @author Alexander Belov(whiterabbit.nsk@gmail.com)
 * @since 03.08.18
 */

/**
 * Класс создает deadlock засчет ожидания разблокировки countDownLatch.
 */
public class GuaranteedDeadLock {
    private CountDownLatch countDownLatch;
    private int threadCount;

    public GuaranteedDeadLock(int threadCount) throws InterruptedException {
        this.threadCount = threadCount;
        countDownLatch = new CountDownLatch(threadCount + 1);
        init();
        countDownLatch.await();
    }

    private void init() {
        System.out.println("Lock will waiting while " + (threadCount + 1) + " threads print 'wait'");
        int count = this.threadCount;
        while (count != 0) {
            new Thread() {
                public void run() {
                    countDownLatch.countDown();
                    System.out.println("wait");
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            count--;
        }
    }

    public static void main(String[] args) {
        try {
            new GuaranteedDeadLock(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
