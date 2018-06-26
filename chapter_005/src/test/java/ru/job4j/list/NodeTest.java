package ru.job4j.list;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class NodeTest {
    @Test
    public void whenContinueCycleThenTrue() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> second = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> fourth = new Node<>(4);

        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = first;

        assertThat(first.hasCycle(first), is(true));
    }

    @Test
    public void whenNotContinueCycleThenFalse() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> second = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> fourth = new Node<>(4);

        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = null;

        assertThat(first.hasCycle(first), is(false));
    }
}
