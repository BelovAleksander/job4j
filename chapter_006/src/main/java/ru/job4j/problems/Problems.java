package ru.job4j.problems;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 08.07.18
 *
 * Класс Problems содержит в себе иллюстрацию типичных
 * ошибок при многопоточном программировании.
 */

public class Problems {
    public int notVolatileField = 20;

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Иллюстрация Состояния Гонки.
     */
    static class RaceCondition implements Runnable {
        public int counter = 0;

        @Override
        public void run() {
            System.out.println("Race condition");
            System.out.println("Thread must print integers from 0 to 10");

            while (this.counter < 11) {
                System.out.println(String.format("Counter: %s", this.counter));
                sleep(1000);
                counter++;
            }
        }
    }

    /**
     * Относится к иллюстрации неиспользования volatile.
     */
    class FirstClass implements Runnable {

        @Override
        public void run() {
            System.out.println(String.format(
                    "first thread: I'm %s years old", notVolatileField));
            sleep(3000);
            System.out.println(String.format(
                    "first thread: I'm %s years old", notVolatileField)); // Вот здесь он вроде как должен использовать
                                                                          // кешированное значение 20, но нет. Выводит 25
        }
    }

    /**
     * Относится к иллюстрации неиспользования volatile.
     */
    class SecondClass implements Runnable {

        @Override
        public void run() {
            notVolatileField = 25;
            System.out.println(String.format(
                    "second thread: After 5 years I am %s years old", notVolatileField));
            sleep(5000);
        }
    }

    /**
     * Относится к иллюстрации DeadLock.
     */
    static class ClassA {
        public synchronized void methodA(ClassB b) {
            System.out.println(String.format("Start methodA...%s", Thread.currentThread().getName()));
            sleep(1000);
            System.out.println("Trying to start methodB in methodA...");
            b.methodC();
        }

        public synchronized void methodC() {
            System.out.println("Start ClassA.methodC...");
        }
    }

    /**
     * Относится к иллюстрации DeadLock.
     */
    static class ClassB {
        public synchronized void methodB(ClassA a) {
            System.out.println(String.format("Start methodB...%s", Thread.currentThread().getName()));
            sleep(1000);
            System.out.println("Trying to start methodA in methodB...");
            a.methodC();
        }
        public synchronized void methodC() {
            System.out.println("Start ClassB.methodC()...");
        }
    }

    /**
     * Взаимная блокировка.
     */
    static class DeadLock implements Runnable {
        ClassA a = new ClassA();
        ClassB b = new ClassB();

        DeadLock() {
            Thread.currentThread().setName("main thread");
            Thread s = new Thread(this, "second thread");
            s.start();
            a.methodA(b);
        }

        @Override
        public void run() {
            b.methodB(a);
        }
    }

    public static void main(String[] args) {
        Problems.RaceCondition race = new Problems.RaceCondition();
        Thread thread1 = new Thread(race);
        thread1.start();
        while (thread1.isAlive()) {
            Problems.sleep(2300);
            race.counter++;
        }

        System.out.println("Start NotVolatileFieldError...");
        Problems.sleep(1000);
        Problems problems = new Problems();
        Thread firstThread = new Thread(problems.new FirstClass());
        Thread secondThread = new Thread(problems.new SecondClass());
        firstThread.start();
        Problems.sleep(1000);
        secondThread.start();

        Problems.sleep(5000);
        System.out.println("Start DeadLock...");
        new Problems.DeadLock();

    }
}
