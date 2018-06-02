package ru.job4j.calculator;

/**
 * Программа расчета идеального веса.
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @ versiion 0.1
 * @ since 26.05.2018
 */

public class Fit {
    /**
     * для мужчин.
     */
    static final double IDEAL_WEIGHT_FOR_MAN = 100.0;
    /**
     * для женщин.
     */
    static final double IDEAL_WEIGHT_FOR_WOMAN = 110.0;
    /**
     * множитель.
     */
    static final double FORMULA_CONSTANT = 1.15;

    /**
     * Идеальный вес для мужчины.
     *
     * @param height Рост.
     * @return идеальный вес.
     */
    public final double manWeight(final double height) {
        return (height - IDEAL_WEIGHT_FOR_MAN) * FORMULA_CONSTANT;
    }

    /**
     * Идеальный вес для женщины.
     *
     * @param height Рост.
     * @return идеальный вес.
     */
    public final double womanWeight(final double height) {
        return (height - IDEAL_WEIGHT_FOR_WOMAN) * FORMULA_CONSTANT;
    }
}
