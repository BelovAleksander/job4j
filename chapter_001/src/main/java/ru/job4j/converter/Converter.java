package ru.job4j.converter;

/** 
* Конвертер валюты.
*/
public class Converter {
	/**
	* Конвертируем рубли в евро.
	* @param value рубли.
	* @return Евро.
	*/
	public final int rubleToEuro(final int value) {
		int course = 70;
		return value / course;
	}
	/**
     * Конвертируем рубли в доллары.
     * @param value рубли.
     * @return Доллары.
     */
	 public final int rubleToDollar(final int value) {
		int course = 60;
	 	return value / course;
	 }
	 /**
	 * Конвертируем евро в рубли.
	 * @param value евро.
	 * @return Рубли.
	 */
	 public final int euroToRuble(final int value) {
		int course = 70;
	 	return value * course;
	 }
	 /**
	 * Конвертируем доллары в рубли.
	 * @param value доллары.
	 * @return Рубли.
	 */
	 public final int dollarToRuble(final int value) {
		int course = 60;
	 	return value * course;
	 }
}
