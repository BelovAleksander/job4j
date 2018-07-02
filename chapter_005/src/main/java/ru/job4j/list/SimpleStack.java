package ru.job4j.list;

public class SimpleStack<E> {
    private DynamicLinkedList<E> list;

    public SimpleStack() {
        this.list = new DynamicLinkedList<>();
    }

    public E poll() {
        return list.isEmpty() ? null : list.removeLast();
    }

    public void push(final E value) {
        list.addFirst(value);
    }
}
