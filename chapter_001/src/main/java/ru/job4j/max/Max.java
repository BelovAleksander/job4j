package ru.job4j.max;

/** 
* @author Alexander Belov (whiterabbit.nsk@gmail.com)
* @version $Id$
* @since 0.1
*/

public class Max {
	/**
	 * Метод возвращает наибольшее значение из двух.
	 * @param first первый аргумент.
	 * @param second второй аргумент.
	 * @return наибольшее число.
	 */
	public final int max(final int first, final int second) {
		return first > second ? first : second;
	}

	/**
	 * Метод возвращает наибольшее значение из трех.
	 * @param first первый аргумент.
	 * @param second второй аргумент.
	 * @param third третий аргумент.
	 * @return наибольшее число.
	 */
	public final int max(final int first, final int second, final int third) {
		return this.max((this.max(first, second)), third);
	}
}
