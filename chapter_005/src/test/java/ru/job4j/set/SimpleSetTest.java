package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleSetTest {
    @Test
    public void whenAddTwoEqualsElementsThenOnlyOneAdded() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(2);
        set.add(3);
        set.add(3);
        set.add(3);
        assertThat(set.size(), is(3));
    }
}
