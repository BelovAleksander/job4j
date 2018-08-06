package ru.job4j.iterator;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ArrayIteratorTest {

    private Iterator<Integer> it;
    private Iterator<Integer> it2;

    @Before
    public void setUp() {
        it = new ArrayIterator(new int[][]{{1, 2, 3}, {4, 5, 6}});
        it2 = new ArrayIterator(new int[][]{{1}, {3, 4}, {7}});
    }
    @Test
    public void hasNextNextSequentialInvocation() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(6));
        assertThat(it.hasNext(), is(false));
    }
    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
    }
    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        boolean result = it.hasNext();
        assertThat(result, is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
    }

    @Test(expected = NoSuchElementException.class)
    public void shoulThrowNoSuchElementException() {
        it = new ArrayIterator(new int[][]{});
        it.next();
    }


    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation2() {
        assertThat(it2.next(), is(1));
        assertThat(it2.next(), is(3));
        assertThat(it2.next(), is(4));
        assertThat(it2.next(), is(7));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder2() {
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.next(), is(1));
        assertThat(it2.next(), is(3));
        assertThat(it2.next(), is(4));
        assertThat(it2.next(), is(7));
    }

    @Test
    public void hasNextNextSequentialInvocation2() {
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.next(), is(1));
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.next(), is(3));
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.next(), is(4));
        assertThat(it2.hasNext(), is(true));
        assertThat(it2.next(), is(7));
        assertThat(it2.hasNext(), is(false));
    }
}
