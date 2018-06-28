package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @param <K> key
 * @param <V> value
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.06.18
 * <p>
 * Класс реализует упрощенный вариант хеш-таблицы,
 * использующий метод цепочек.
 */

public class SimpleHashTable<K, V> implements Iterable {
    private Object[] array;
    /**
     * счетчик элементов данных
     */
    private int counter = 0;

    public SimpleHashTable() {
        this.array = new Object[19];
    }

    /**
     * Хеш-функция.
     *
     * @param key объект
     * @return хеш-код
     */
    private int hashFunc(K key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return (h ^ (h >>> 7) ^ (h >>> 4)) % array.length;
    }

    /**
     * Метод добавляет елемент в массив.
     *
     * @param value элемент данных
     * @return true, если успешно
     */
    public boolean add(K key, V value) {
        boolean result = false;
        Link newLink = new Link(key, value);
        Link oldLink = (Link) this.array[newLink.hash];

        if (oldLink == null) {
            this.array[newLink.hash] = newLink;
            result = true;
            this.counter++;
        } else if (!oldLink.equals(newLink)) {
            while (oldLink.next != null) {
                oldLink = oldLink.next;
            }
            oldLink.next = newLink;
            result = true;
            this.counter++;
        }
        return result;
    }

    /**
     * Находит заданный элемент в массиве по ключу
     *
     * @param key ключ
     * @return значение
     */
    public V getValue(K key) {
        int hashVal = this.hashFunc(key);
        Link oldLink = (Link) this.array[hashVal];
        V result = null;
        if (oldLink != null) {
            if (oldLink.key.equals(key)) {
                result = oldLink.value;
            } else {
                while (oldLink.next != null) {
                    oldLink = oldLink.next;
                    if (oldLink.key.equals(key)) {
                        result = oldLink.value;
                        break;
                    }
                }
            }
        }
        if (result == null) {
            throw new NoSuchElementException("No such element!");
        }
        return result;
    }

    /**
     * Удаляет ключ из множества и значение по ключу.
     *
     * @param key ключ
     * @return true, если успешно
     */
    public boolean remove(K key) {
        boolean result = false;
        int hashVal = this.hashFunc(key);
        Link oldLink = (Link) this.array[hashVal];
        if (oldLink != null) {
            if (oldLink.key.equals(key)) {
                oldLink = oldLink.next;
                result = true;
                this.counter--;
            } else {
                while (oldLink.next != null) {
                    oldLink = oldLink.next;
                    if (oldLink.key.equals(key)) {
                        oldLink = oldLink.next;
                        result = true;
                        this.counter--;
                        break;
                    }
                }
            }
        }
        return result;
    }


    /**
     * Возвращает размер.
     *
     * @return размер
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * Внутренний класс, являющийся
     * базовым элементом внутреннего массива.
     */
    private class Link {
        private V value;
        private K key;
        public Link next;
        private int hash;

        public Link(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = hashFunc(key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Link link = (Link) o;
            return Objects.equals(key, link.key);
        }

        @Override
        public int hashCode() {

            return Objects.hash(value, key, hash);
        }

    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int itCount = 0;
            Link link = (Link) array[itCount];

            @Override
            public boolean hasNext() {
                return ((itCount < array.length - 1) || (link.next != null));
            }

            @Override
            public K next() {
                if (link.next != null) {
                    link = link.next;
                }
                return link.key;
            }
        };
    }
}
