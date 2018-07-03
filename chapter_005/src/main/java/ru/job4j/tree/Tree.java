package ru.job4j.tree;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 */

public class Tree<E extends Comparable<E>> implements SimpleTree<E>, Iterable<E> {
    private Node<E> root;
    private List<Node<E>> list;
    private boolean binary = true;

    public Tree(E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        Node<E> father = findBy(parent).get();
        Node<E> son = new Node<>(child);
        father.add(son);
        return false;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    private void treeTraversal(Node<E> parent) {
        Node<E> node = parent;
        List<Node<E>> array = node.leaves();
        list.add(parent);
        if (!array.isEmpty()) {
            for (Node<E> child : array) {
                treeTraversal(child);
            }
        }
        if (array.size() > 2) {
            this.binary = false;
        }

    }

    public boolean isBinary() {
        list = new ArrayList<>();
        treeTraversal(root);
        return binary;
    }

    @Override
    public Iterator iterator() {
        this.list = new ArrayList<>();
        treeTraversal(root);
        return list.iterator();
    }
}
