package ru.job4j.calculator;

/** 
* Программа расчета идеального веса.
* @author Alexander Belov (whiterabbit.nsk@gmail.com)
* @ versiion 0.1
* @ since 26.05.2018
*/

public class Fit {
	/**
	* Идеальный вес для мужчины.
	* @param height Рост.
	* @return идеальный вес.
	*/
	public final double manWeight(final double height) {
		return (height - 100) * 1.15;
	}
	/**
	* Идеальный вес для женщины.
	* @param height Рост.
	* @return идеальный вес.
	*/
	public final double womanWeight(final double height) {
		return (height - 110) * 1.15;
	}
}
