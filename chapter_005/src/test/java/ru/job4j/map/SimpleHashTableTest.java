package ru.job4j.map;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleHashTableTest {

    @Test
    public void whenAddElementThenReturnTrue() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        assertThat(test.add(1, 10), is(true));
    }

    @Test
    public void whenRemoveNonexistentElementThenReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        assertThat(test.remove(5), is(false));
    }

    @Test
    public void whenAddDuplicateKeyThenReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        assertThat(test.add(1, 15), is(false));
    }

    @Test
    public void whenAddDuplicateKeyThenWillNotAdded() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        test.add(1, 15);
        assertThat(test.getCounter(), is(1));
    }

    @Test
    public void whenInsertAndGetThenReturn() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        Integer result = test.getValue(1);
        assertThat(result, is(10));
    }

    @Test
    public void whenInsertTwoElementsThenSizeIs2() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        test.add(2, 20);
        assertThat(test.getCounter(), is(2));
    }

    @Test
    public void whenInsertAndDeleteThenSizeIs0() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        test.remove(1);
        assertThat(test.getCounter(), is(0));
    }

    @Test
    public void whenInsertDuplicateElementThenReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        test.add(1, 10);
        assertThat(test.add(1, 10), is(false));
    }

    @Test
    public void whenNoSuchElementsThenDeleteReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>();
        assertThat(test.remove(2), is(false));
    }
}
