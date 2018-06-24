package ru.job4j.iterator;

import java.util.Iterator;
public class Converter {
    private boolean newItStart = true;
    private Iterator<Integer> innerIt;
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        // Iterator<Integer> innerIt = it.hasNext();
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return (it.hasNext() || innerIt.hasNext()); // не проходит тест на пустые кейсы
            } // пробовал убрать it.hasNext() в совокупности с 8-ой строкой и перестановкой
            // условий в next(). Тогда вылетает другой тест hasNextNextSequentialInvocation(), потому  что
            // hasNext() по прохождении первого итератора не видит, что в кейсе  есть еще.
            // Не вижу других решений, кроме копирования данных, прохождению в hasNext() while-ом
            // всего кейса итераторов, дабы убедиться, что они не пустые.

            @Override
            public Integer next() {
                if (newItStart) {
                    innerIt = it.next();
                }
                int result = innerIt.next();
                if (innerIt.hasNext()) {
                    newItStart = false;
                } else {
                    newItStart = true;
                }
                return result;
            }
        };
    }
}
