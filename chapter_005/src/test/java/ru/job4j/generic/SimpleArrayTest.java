package ru.job4j.generic;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleArrayTest {
    @Test
    public void whenAddElementThenGetItWithIndex0() {
        DynamicArray<Integer> array = new DynamicArray<>(5);
        array.add(3);
        int result = array.get(0);
        assertThat(result, is(3));
    }
    @Test
    public void whenSetStringWithIndex3ThenGetIt() {
        DynamicArray<String> array = new DynamicArray<>(5);
        array.set(3, "test");
        String result = array.get(3);
        assertThat(result, is("test"));
    }
    @Test
    public void whenDeleteElementThenGetMethodReturnNull() {
        DynamicArray<Integer> array = new DynamicArray<>(5);
        array.add(3);
        array.add(5);
        array.delete(0);
        Integer number = array.get(0);
        boolean result = number == null;
        assertThat(result, is(true));
    }
    @Test(expected = StackOverflowError.class)
    public void whenAddMoreElementsThanArraySizeThenThrowException() {
        DynamicArray<Integer> array = new DynamicArray<>(1);
        array.add(1);
        array.add(2);
    }
}
