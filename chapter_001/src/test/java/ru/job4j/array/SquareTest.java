package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SquareTest {
    @Test
    public void whenBound3Then149() {
        int bound = 3;
        Square square = new Square();
        int[] rst = square.calculate(bound);
        int[] expect = new int[] {1, 4, 9};
        assertThat(rst, is(expect));
    }
    @Test
    public void whenBound5Then1491625() {
        int bound = 5;
        Square square = new Square();
        int[] rst = square.calculate(5);
        int[] expected = new int[] {1,4,9,16,25};
        assertThat(rst, is(expected));
    }
    @Test
    public void whenBound2Then14() {
        int bound = 2;
        Square square = new Square();
        int[] rst = square.calculate(2);
        int[] expected = new int[] {1,4};
        assertThat(rst, is(expected));
    }
}
