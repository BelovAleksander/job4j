package ru.job4j.max;

/** 
* @author Alexander Belov (whiterabbit.nsk@gmail.com)
* @version $Id$
* @since 0.1
*/

public class Max {
	/**
	* Метод вычисляет максимальное значение из двух
	* @param first первое значение, second второе значение 
	* @return максимальное значение
	*/
	public int max(int first, int second) {
		int rsl;
		first > second ? rsl = first : rsl = second;
		return rsl;
	}
}
