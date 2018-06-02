package ru.job4j.condition;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Point {
    /**
     * координаты по оси х.
     */
    private int x;
    /**
     * координаты по оси у.
     */
    private int y;
    /**
     * Конструктор.
     * @param a ось х.
     * @param b ось у.
     */
    public Point(final int a, final int b) {
        this.x = a;
        this.y = b;
    }
    /**
     * Измеряет дистанцию.
     * @param that точка.
     * @return дистанция.
     */
    public final double distanceTo(final Point that) {
        return Math.sqrt(
                Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2)
        );
    }

    /**
     * Main.
     * @param args user in.
     */
    public static void main(final String[] args) {
        Point a = new Point(0, 1);
        Point b = new Point(2, 1);
        // сделаем вызов метода
        System.out.println("x1 = " + a.x);
        System.out.println("y1 = " + a.y);
        System.out.println("x2 = " + b.x);
        System.out.println("y2 = " + b.y);

        double result = a.distanceTo(b);
        System.out.println("Расстояние между точками А и В : " + result);
    }
}
