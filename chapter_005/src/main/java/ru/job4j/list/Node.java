package ru.job4j.list;

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

    /**
     * Метод проверяет входящий объект на зацикленнссть.
     *
     * @param first объект Node
     * @return true, если зациклен
     */
    public boolean hasCycle(Node<E> first) {
        boolean result = true;
        Node<E> test = first;
        for (int i = 0; i < counter; i++) {
            test = test.next;
            if (test == null) {
                result = false;
                break;
            }
        }
        return result;
    }
}
