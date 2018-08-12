package ru.job4j.map;

import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleHashTableTest {

    @Test(expected = NoSuchElementException.class)
    public void whenRemoveElementThenNoSuchElementException() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 5);
        test.add(4, 10);
        System.out.println(test.getValue(1));
        test.remove(1);
        test.getValue(1);
    }

    @Test
    public void whenAddDuplicateThenValueIsOverwriting() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 5);
        test.add(1, 10);
        test.add(1, 15);
        assertThat(test.getValue(1), is(15));
    }

    @Test
    public void whenAddElementThenReturnTrue() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        assertThat(test.add(1, 10), is(true));
    }

    @Test
    public void whenRemoveNonexistentElementThenReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 10);
        assertThat(test.remove(5), is(false));
    }

    @Test
    public void whenAddDuplicateKeyThenReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 10);
        assertThat(test.add(1, 15), is(false));
    }

    @Test
    public void whenInsertAndGetThenReturnValue() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 10);
        Integer result = test.getValue(1);
        assertThat(result, is(10));
    }

    @Test
    public void whenInsertTwoElementsThenSizeIs2() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 10);
        test.add(2, 20);
        assertThat(test.getCounter(), is(2));
    }

    @Test
    public void whenInsertAndDeleteThenSizeIs0() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 10);
        test.remove(1);
        assertThat(test.getCounter(), is(0));
    }


    @Test
    public void whenNoSuchElementsThenDeleteReturnFalse() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        assertThat(test.remove(2), is(false));
    }


    @Test
    public void whenAdd5ElementsThenIterateThem() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        test.add(1, 11);
        test.add(2, 12);
        test.add(3, 13);
        test.add(4, 14);
        test.add(5, 15);
        Iterator it = test.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElementsAndWeCallNextThenNoSuchElementException() {
        SimpleHashTable<Integer, Integer> test = new SimpleHashTable<>(3);
        Iterator it = test.iterator();
        it.next();
        it.next();
        it.next();
        it.next();
    }
}
