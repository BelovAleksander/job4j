package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BubbleSortTest {
    @Test
    public void when321Then123() {
        BubbleSort bubble = new BubbleSort();
        int[] input = new int[] {3, 2, 1};
        int[] result = bubble.sort(input);
        int[] expected = {1, 2, 3};
        assertThat(result, is(expected));
    }
     @Test
    public void when32751Then12357() {
        BubbleSort bubble = new BubbleSort();
        int[] input = new int[] {3, 2, 7, 5, 1};
        int[] result = bubble.sort(input);
        int[] expected = {1, 2, 3, 5, 7};
        assertThat(result, is(expected));
     }
}
