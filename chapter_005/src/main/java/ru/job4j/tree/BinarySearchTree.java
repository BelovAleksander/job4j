package ru.job4j.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 *
 * Класс представляет модель двоичного дерева поиска
 * с методом add().
 * @param <E> тип данных, реализующий интерфейс Comparable
 */

public class BinarySearchTree<E extends Comparable<E>> implements Iterable {
    private BinaryNode<E> root;
    private List<E> list;

    public BinarySearchTree(E root) {
        this.root = new BinaryNode<>(root);
    }

    /**
     * Добавляет элементы по принципу:
     * value <= влево ; value > вправо.
     * @param value новое значение
     * @return true если успешно.
     */
    public boolean add(E value) {
        boolean result = false;
        BinaryNode<E> current = root;
        BinaryNode<E> parent = root;
        while (true) {
            int var = current.eqValue(value);
            if (var > 0 || var == 0) {
                parent = current;
                current = current.leftChild;
                if (current == null) {
                    parent.leftChild = new BinaryNode<>(value);
                    result = true;
                    break;
                }
            } else {
                parent = current;
                current = current.rightChild;
                if (current == null) {
                    parent.rightChild = new BinaryNode<>(value);
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Симметричный обход дерева с занесением результатов в list.
     * @param parent корень обходимого дерева/поддерева.
     */
    private void treeTraversal(BinaryNode<E> parent) {
        if (parent.leftChild != null) {
            treeTraversal(parent.leftChild);
        }
        list.add(parent.getValue());
        if (parent.rightChild != null) {
            treeTraversal(parent.rightChild);
        }
    }

    /**
     * Итератор обходит list, содержащий в себе
     * результаты симметричного обхода дерева.
     * @return итератор list'a
     */
    @Override
    public Iterator iterator() {
        list = new ArrayList<>();
        treeTraversal(root);
        return list.iterator();
    }
}
