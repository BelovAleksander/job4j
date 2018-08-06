package ru.job4j.tree;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 */

public class Tree<E extends Comparable<E>> implements SimpleTree<E>, Iterable<E> {
    private Node<E> root;
    private List<Node<E>> list;

    public Tree(E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = true;
        Node<E> father = findBy(parent).get();
        Node<E> son = new Node<>(child);
        for (Node<E> node : father.leaves()) {
            if (node.eqValue(child)) {
                result = false;
                break;
            }
        }
        if (result) {
            father.add(son);
        }
        return result;
    }

    private ArrayList<Node<E>> treeTraversal() {
        ArrayList<Node<E>> result = new ArrayList<>();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            result.add(el);
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return result;
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

    @Override
    public Iterator iterator() {
        this.list = treeTraversal();
        return list.iterator();
    }
}
