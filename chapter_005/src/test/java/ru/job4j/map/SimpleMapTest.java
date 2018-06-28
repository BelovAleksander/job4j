package ru.job4j.map;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleMapTest {
    @Test
    public void whenInsertAndGetThenReturn() {
        SimpleMap<Integer, Integer> test = new SimpleMap<>();
        test.insert(1, 10);
        Integer result = test.get(1);
        assertThat(result, is(10));
    }

    @Test
    public void whenInsertTwoElementsThenSizeIs2() {
        SimpleMap<Integer, Integer> test = new SimpleMap<>();
        test.insert(1, 10);
        test.insert(2, 20);
        assertThat(test.getSize(), is(2));
    }

    @Test
    public void whenInsertAndDeleteThenSizeIs0() {
        SimpleMap<Integer, Integer> test = new SimpleMap<>();
        test.insert(1, 10);
        test.delete(1);
        assertThat(test.getSize(), is(0));
    }

    @Test
    public void whenInsertDublicatElementThenReturnFalse() {
        SimpleMap<Integer, Integer> test = new SimpleMap<>();
        test.insert(1, 10);
        assertThat(test.insert(1, 10), is(false));
    }

    @Test
    public void whenNoSuchElementsThenDeleteReturnFalse() {
        SimpleMap<Integer, Integer> test = new SimpleMap<>();
        assertThat(test.delete(2), is(false));
    }
}
