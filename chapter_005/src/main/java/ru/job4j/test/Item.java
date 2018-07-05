package ru.job4j.test;

import java.util.Objects;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 */
public class Item implements Comparable {
    private int id;
    private static int counter = 0;
    /**
     * эммитент
     */
    private final String book;
    /**
     * добавление/удаление
     */
    private final boolean add;
    /**
     * покупка/продажа
     */
    private final boolean ask;
    /**
     * цена
     */
    private final double price;
    /**
     * объем
     */
    private int volume;

    protected Item(String book, boolean add, boolean ask,
                double price, int volume) {
        this.book = book;
        this.add = add;
        this.ask = ask;
        this.price = price;
        this.volume = volume;
        counter++;
        this.id = counter;
    }

    public String getBook() {
        return this.book;
    }

    public boolean isAdd() {
        return add;
    }

    public boolean isAsk() {
        return ask;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(Object o) {
        Item item = (Item) o;
        return (int) (this.getPrice() - item.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return  id == item.id
                && add == item.add
                && ask == item.ask
                && Double.compare(item.price, price) == 0
                && volume == item.volume
                && Objects.equals(book, item.book);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, book, add, ask, price, volume);
    }
}
