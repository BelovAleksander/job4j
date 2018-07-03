package ru.job4j.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E>, Iterable<E> {
    private Node<E> root;
    private List<Node<E>> list;

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

    public void treeTraversal(Node<E> parent) {
        Node<E> node = parent;
        if (!node.leaves().isEmpty()) {
            for (Node<E> child : node.leaves()) {
                treeTraversal(child);
            }
        }
        list.add(parent);
    }

    @Override
    public Iterator iterator() {
        this.list = new ArrayList<>();
        treeTraversal(root);
        return list.iterator();
    }
}
