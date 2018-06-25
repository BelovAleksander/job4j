package ru.job4j.list;

public class SimpleStack<E> {
    private DynamicLinkedList<E> list;

    public SimpleStack() {
        this.list = new DynamicLinkedList<>();
    }

    public E poll() {
        return list.isEmpty() ? null : list.removeFirst();
    }

    public void push(final E value) {
        list.add(value);
    }
}
