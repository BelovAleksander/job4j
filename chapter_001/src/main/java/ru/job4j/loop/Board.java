package ru.job4j.loop;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Board {

    /**
     * Создает шахматную доску с заданными параметрами.
     *
     * @param width  ширина доски.
     * @param height высота доски.
     * @return доска в строковом представлении.
     */
    public final String paint(final int width, final int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    screen.append("X");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(ln);
        }
        return screen.toString();
    }
}
