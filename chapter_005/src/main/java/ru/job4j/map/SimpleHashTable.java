package ru.job4j.map;

import java.util.Iterator;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.06.18
 *
 * Класс реализует упрощенный вариант хеш-таблицы,
 * реализующей двойное хеширование.
 * @param <K> key
 * @param <V> value
 */

public class SimpleHashTable<K, V> implements Iterable {
    private Object[] values;
    private Object[] keys;
    /**
     * счетчик элементов данных
     */
    private int counter = 0;
    /**
     * указатель на пустую ячейку
     * используется в методе add(E value)
     */
    private int emptyCell;

    public SimpleHashTable() {
        this.values = new Object[19];
        this.keys = new Object[19];
    }

    /**
     * Хеш-функция.
     * @param key объект
     * @return хеш-код
     */
    private int firstHashFunction(K key) {
        return key.hashCode() % this.values.length;
    }

    /**
     * Хеш-функция для пробирования.
     * @param key объект
     * @return хеш-код
     */
    private int secondHashFunction(K key) {
        return 3 - (key.hashCode() % 3);
    }

    /**
     * Метод подбирает ближайшее большее простое число.
     * @param min число
     * @return простое число
     */
    private int getPrime(int min) {
        for (int j = min + 1; true; j++) {
            if (isPrime(j)) {
                return j;
            }
        }
    }

    /**
     * Этот метод своровал из учебника.
     * @param n число
     * @return true если простое
     */
    private boolean isPrime(int n) {
        for (int j = 2; (j * j <= n); j++) {
            if (n % j == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод добавляет елемент в массив.
     * Вызывает grow(), если коэффициент заполнения 66%
     * @param value элемент данных
     * @return true, если успешно
     */
    public boolean add(K key, V value) {
        boolean isDublicat = false;
        for (Object el : this.keys) {
            if (key.equals((K) el)) {
                isDublicat = true;
                break;
            }
        }
        int index = 0;
        if (!isDublicat) {
            index = this.findElement(key);
        }
        if (index == -1) {
            this.values[this.emptyCell] = value;
            this.keys[this.emptyCell] = key;
            this.counter++;
            if (this.values.length < this.counter * 1.5) {
                this.grow();
            }
        }
        return index == -1;
    }

    /**
     * Находит заданный элемент в массиве по ключу
     * @param key ключ
     * @return значение
     */
    private int findElement(K key) {
        int result = -1;
        int firstValue = firstHashFunction(key);
        int secondValue = secondHashFunction(key);
        while (this.keys[firstValue] != null) {
            if (this.keys[firstValue].equals(key)) {
                result = firstValue;
                break;
            }
            firstValue += secondValue;
            if (firstValue >= this.keys.length) {
                firstValue = firstValue % this.keys.length;
            }
        }
        this.emptyCell = firstValue;
        return result;
    }

    /**
     * Возвращает значение по ключу.
     * @param key ключ
     * @return значение
     */
    public V getValue(K key) {
        int index = findElement(key);
        return index == -1 ? null : (V) values[index];
    }

    /**
     * Удаляет ключ из множества и значение по ключу.
     * @param key ключ
     * @return true, если успешно
     */
    public boolean remove(K key) {
        int index = findElement(key);
        if (index != -1) {
            this.values[index] = null;
            this.counter--;
            this.keys[index] = null;
        }
        return index != -1;
    }

    /**
     * Увеличивает размер массива до простого числа
     * чуть более чем в 2 раза.
     */
    private void grow() {
        Object[] oldValues = this.values;
        Object[] oldKeys = this.keys;
        this.values = new Object[getPrime(this.values.length * 2)];
        this.keys = new Object[this.values.length];
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                this.add((K) oldKeys[i], (V) oldValues[i]);
            }
        }
    }

    /**
     * Возвращает размер.
     * @return размер
     */
    public int getCounter() {
        return this.counter;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int itCount = 0;
            @Override
            public boolean hasNext() {
                return itCount < keys.length - 1;
            }

            @Override
            public K next() {
                return (K) keys[itCount++];
            }
        };
    }
}
