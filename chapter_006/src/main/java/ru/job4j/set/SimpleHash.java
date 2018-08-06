package ru.job4j.set;

/**
 * Класс реализует упрощенный вариант хеш-таблицы,
 * реализующей двойное хеширование.
 *
 * @param <E> Любой тип
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.06.18
 */

public class SimpleHash<E> {
    private Object[] array;
    /**
     * счетчик элементов данных
     */
    private int counter;
    /**
     * указатель на пустую ячейку
     * используется в методе add(E value)
     */
    private int emptyCell;
    private int size;

    public SimpleHash(int size) {
        this.array = new Object[size];
        this.size = size;
    }

    /**
     * Хеш-функция.
     * @param key объект
     * @return хеш-код
     */
    private int firstHashFunction(E key) {
        return key.hashCode() % this.array.length;
    }

    /**
     * Хеш-функция для пробирования.
     * @param key объект
     * @return хеш-код
     */
    private int secondHashFunction(E key) {
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
    public boolean add(E value) {
        int index = findElement(value);
        if (index == -1) {
            this.array[this.emptyCell] = value;
            this.counter++;
            if (this.array.length < this.counter * 1.5) {
                this.grow();
            }
        }
        return index == -1;
    }

    /**
     * Находит заданный элеммент в массиве.
     * @param value искомый элемент
     * @return возвращает индекс, если успешно, -1 если нет
     */
    public int findElement(E value) {
        int result = -1;
        int firstValue = firstHashFunction(value);
        int secondValue = secondHashFunction(value);
        while (this.array[firstValue] != null) {
            if (this.array[firstValue].equals(value)) {
                result = firstValue;
                break;
            }
            firstValue += secondValue;
            if (firstValue >= this.array.length) {
                firstValue -= this.array.length;
            }
        }
        this.emptyCell = firstValue;
        return result;
    }

    public boolean find(E value) {
        return findElement(value) != -1;
    }

    public boolean remove(E value) {
        int index = findElement(value);
        if (index != -1) {
            this.array[index] = null;
            this.counter--;
        }
        return index != -1;
    }

    /**
     * Увеличивает размер массива до простого числа
     * чуть более чем в 2 раза.
     */
    private void grow() {
        Object[] oldArray = this.array;
        this.array = new Object[getPrime(this.array.length * 2)];
        this.size = this.array.length;
        for (Object value : oldArray) {
            if (value != null) {
                add((E) value);
            }
        }
    }

    public int getSize() {
        return this.size;
    }
}
