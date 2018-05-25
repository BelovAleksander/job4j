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
	public double manWeight(double height) {
		return (height - 100) * 1.15;
	}
	
	/**
	* Идеальный вес для женщины.
	* @param height Рост.
	* @return идеальный вес.
	*/
	public double womanWeight(double height) {
		return (height - 110) * 1.15;
	}
}
