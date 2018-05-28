package ru.job4j.loop;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Board {

	/**
	 * Создает шахматную доску с заданными параметрами.
	 * @param width ширина доски.
	 * @param height высота доски.
	 * @return доска в строковом представлении.
	 */
	public String paint(int width, int height) {
		StringBuilder screen = new StringBuilder();
		String ln = System.lineSeparator();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// условие проверки, что писать пробел или X.
				if ((i + j) % 2 == 0) {
					screen.append("X");
				}
				else {
					screen.append(" ");
				}
			}
			// добавляем перевод на новую строку.
			screen.append(ln);
		}
		return screen.toString();
	}
}