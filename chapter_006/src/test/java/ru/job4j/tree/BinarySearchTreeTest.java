package ru.job4j.tree;

import org.junit.Test;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BinarySearchTreeTest {

    @Test
    public void whenAddElementThenReturnTrue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(50);
        assertThat(tree.add(50), is(true));
    }

    @Test
    public void whenAdd25And25And75ThenNextReturn25And25And50And75() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(50);
        tree.add(25);
        tree.add(25);
        tree.add(75);
        Iterator it = tree.iterator();
        assertThat(it.next(), is(25));
        assertThat(it.next(), is(25));
        assertThat(it.next(), is(50));
        assertThat(it.next(), is(75));
    }
}