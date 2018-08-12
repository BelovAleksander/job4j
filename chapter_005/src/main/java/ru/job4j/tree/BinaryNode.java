package ru.job4j.tree;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 */

public class BinaryNode<E extends Comparable<E>> {
    private final E value;
    public BinaryNode<E> leftChild;
    public BinaryNode<E> rightChild;

    public BinaryNode(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public int eqValue(E that) {
        return this.value.compareTo(that);
    }
}
