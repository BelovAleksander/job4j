package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class BoardTest {
    @Test
    public void when3x3() {
        Board board = new Board();
        String result = board.paint(3,3);
        String ln = System.lineSeparator();
        String expected = String.format("X X%s X %sX X%s", ln, ln, ln);
        assertThat(result, is(expected));
    }
    @Test
    public void when5x5() {
        Board board = new Board();
        String result = board.paint(5,5);
        String ln = System.lineSeparator();
        String expected = String.format("X X X%s X X %sX X X%s X X %sX X X%s", ln, ln, ln, ln, ln);
        assertThat(result, is(expected));
    }
    @Test
    public void whenPaintBoardWithWidthFiveAndHeightFourThenStringWithFiveColsAndFourRows() {
        Board board = new Board();
        String result = board.paint(5,4);
        String ln = System.lineSeparator();
        String expected = String.format("X X X%s X X %sX X X%s X X %s", ln, ln, ln, ln);
        assertThat(result, is(expected));
    }
}
