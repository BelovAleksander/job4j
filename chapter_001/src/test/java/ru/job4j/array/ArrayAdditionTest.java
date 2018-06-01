package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayAdditionTest {
    @Test
    public void whenFirstArrayMoreThenSecond() {
        ArrayAddition addition = new ArrayAddition();
        int[] first = new int[] {1, 3, 4, 8, 10};
        int[] second = new int[] {2, 5};
        int[] result = addition.summa(first, second);
        int[] exception = new int[] {1, 2, 3, 4, 5, 8, 10};
        assertThat(result, is(exception));
    }
    @Test
    public void whenSecondArrayMoreThenFirst() {
        ArrayAddition addition = new ArrayAddition();
        int[] first = new int[] {1, 3, 4};
        int[] second = new int[] {2, 5, 7, 8, 10};
        int[] result = addition.summa(first, second);
        int[] exception = new int[] {1, 2, 3, 4, 5, 7, 8, 10};
        assertThat(result, is(exception));
    }
}
