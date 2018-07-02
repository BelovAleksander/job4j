package ru.job4j.list;

import java.util.Objects;

/**
 * @param <E> любой тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 26.06.18
 * <p>
 * Класс создает объекты Node, затем проверяет их на зацикленность.
 */
public class Node<E> {
    private E value;
    public Node<E> next;
    /**
     * Счетчик экземпляров класса
     */
    private static int counter = 0;

    public Node(E value) {
        this.value = value;
        counter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value)
                && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, next);
    }

    /**
     * Метод проверяет входящий объект на зацикленнссть.
     *
     * @param first объект Node
     * @return true, если зациклен
     */
    public boolean hasCycle(Node<E> first) {
        boolean result = false;
        Node<E> turtle = first;
        Node<E> rabbit = first;
        while (rabbit != null && rabbit.next != null) {
            turtle = turtle.next;
            rabbit = rabbit.next.next;
            if (turtle.equals(rabbit)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
