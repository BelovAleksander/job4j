package ru.job4j.condition;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Triangle {
    /**
     * a.
     */
    private Point a;
    /**
     * b.
     */
    private Point b;
    /**
     * c.
     */
    private Point c;

    /**
     * Конструктор.
     *
     * @param x длинна.
     * @param y ширина.
     * @param z высота.
     */
    public Triangle(final Point x, final Point y, final Point z) {
        this.a = x;
        this.b = y;
        this.c = z;
    }

    /**
     * Метод вычисления полупериметра по длинам сторон.
     * <p>
     * Формула.
     * <p>
     * (ab + ac + bc) / 2
     *
     * @param ab расстояние между точками a b.
     * @param ac расстояние между точками a c.
     * @param bc расстояние между точками b c.
     * @return Полупериметр.
     */
    public final double period(final double ab, final double ac,
                               final double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * Метод должен вычислить площадь треугольника.
     *
     * @return Вернуть прощадь, если треугольник
     * существует или -1, если треугольника нет.
     */
    public final double area() {
        double rsl = -1;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            rsl = Math.sqrt(
                    p * (p - ab) * (p - ac) * (p - bc)
            );
        }
        return rsl;
    }

    /**
     * Метод проверяет можно ли построить треугольник
     * с такими длинами сторон.
     * Подумайте какое надо написать условие, чтобы
     * определить можно ли построить треугольник.
     *
     * @param ab Длина от точки a b.
     * @param ac Длина от точки a c.
     * @param bc Длина от точки b c.
     * @return Логическое значение.
     */
    private boolean exist(final double ab, final double ac,
                          final double bc) {
        return ((ab + ac) > bc) && ((ab + bc) > ac) && ((ac + bc) > ab);
    }
}
