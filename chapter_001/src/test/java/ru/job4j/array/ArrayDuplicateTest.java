package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] result = duplicate.remove(input);
        String[] expected = new String[] {"Привет", "Мир", "Супер"};
        assertThat(result, is(expected));
    }
    @Test
    public void whenNoDuplicatesThenArrayWithoutDuplicate2() {
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Мир", "Супер"};
        String[] result = duplicate.remove(input);
        String[] expected = new String[] {"Привет", "Мир", "Супер"};
        assertThat(result, is(expected));
    }
    @Test
    public void whenNoDuplicatesThenArrayWithoutDuplicate25() {
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] input = new String[] {"Супер", "Супер", "Супер"};
        String[] result = duplicate.remove(input);
        String[] expected = new String[] {"Супер"};
        assertThat(result, is(expected));
    }
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate252() {
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Привет", "Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] result = duplicate.remove(input);
        String[] expected = new String[] {"Привет", "Мир", "Супер"};
        assertThat(result, is(expected));
    }
}
