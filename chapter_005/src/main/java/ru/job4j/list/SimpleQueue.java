package ru.job4j.list;

public class SimpleQueue<E> {
    private DynamicLinkedList<E> list;

    public SimpleQueue() {
        this.list = new DynamicLinkedList<>();
    }

    public E poll() {
        return list.isEmpty() ? null : list.removeFirst();
    }

    public void push(final E value) {
        list.addFirst(value);
    }
}
