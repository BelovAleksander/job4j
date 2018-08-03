package ru.job4j.concurrency;

/**
 * @author Alexander Belov(whiterabbit.nsk@gmail.com)
 * @since 03.08.18
 */

/**
 * Класс создает две нити, которые поочередно добавляют в объект внутреннего класса Base по 10 значений.
 */
public class Switcher {
    public void start() throws InterruptedException {
        Base base = new Base();

        Thread thread1 = new MyThread(1, base);
        Thread thread2 = new MyThread(2, base);
        thread1.start();
        thread2.start();

        Thread.currentThread().sleep(1000);
        thread1.interrupt();
        thread2.interrupt();
        System.out.println(base.getString());
    }

    class Base {
        private String string = "";
        private int count = 0;

        public synchronized void add(int value) throws InterruptedException {
            if (count != 0 && !string.endsWith("" + value)) {
                wait();
            }
            count++;
            String newString = this.string + value;
            this.string = newString;
            if (count == 10) {
                count = 0;
                notifyAll();
                wait();
            }
        }

        public String getString() {
            return this.string;
        }

    }

    class MyThread extends Thread {
        private int value;
        private Base base;
        public MyThread(int value, Base base) {
            this.value = value;
            this.base = base;
        }

        public void run() {
            int i = 0;
            while (i < 30) {
                try {
                    base.add(value);
                    i++;
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().toString() + " was interrupted");
                }
            }
            System.out.println(Thread.currentThread().toString() + " stopped");
        }
    }

    public static void main(String[] args) {
        try {
            new Switcher().start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
