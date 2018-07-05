package ru.job4j.test;

import java.util.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.07.18
 *
 * Класс отображает биржевой стакан.
 */

public class Glass {
    private String name;
    private TreeSet<Item> ask = new TreeSet<>(new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            int result = (int) (o1.getPrice() - o2.getPrice());
            return result != 0 ? result : o1.getId() - o2.getId();
        }
    });
    private Set<Item> bid = new TreeSet<>(new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            int result = (int) (o1.getPrice() - o2.getPrice());
            return result != 0 ? result : o1.getId() - o2.getId();
        }
    });


    protected Glass(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Принятие заявки в обработку.
     * В зависимости от типа удаляет/добавляет заявку.
     * @param item заявка
     * @return true если успешно
     */
    public boolean process(Item item) {
        boolean result;
        if (item.isAdd() && item.isAsk()) {
            result = add(item, bid, ask);
        } else if (item.isAdd() && !item.isAsk()) {
            result = add(item, ask, bid);
        } else {
            result = delete(item);
        }
        return result;
    }

    /**
     * Добавляет заявку. В зависимости от типа операции
     * добавляет в соответствующее множество заявок.
     * @param item заявка
     * @param oppositeList множество с противоположными заявками
     * @param sameList множество с заявками схожего типа
     * @return true если успешно
     */
    private boolean add(Item item, Set<Item> oppositeList, Set<Item> sameList) {
        boolean result = false;
        double price = item.getPrice();
        int volume = item.getVolume();

        for (Item el : oppositeList) {
            if (el.getPrice() == price) {
                if (el.getVolume() > volume) {
                    el.setVolume(el.getVolume() - volume);
                    result = true;
                    break;
                } else if (el.getVolume() < volume) {
                    item.setVolume(volume - el.getVolume());
                    oppositeList.remove(el);
                    sameList.add(item);
                    result = true;
                    break;
                } else {
                    oppositeList.remove(el);
                    result = true;
                    break;
                }
            }
        }
        if (!result) {
            sameList.add(item);
            result = true;
        }

        return result;
    }

    /**
     * Удаляет заявку
     * @param item заявка
     * @return true если успешно
     */
    public boolean delete(Item item) {
        boolean result;
        Item delItem = null;
        if (item.isAsk()) {
            for (Item el : ask) {
                if (el.getVolume() == item.getVolume()
                        && el.getPrice() == item.getPrice()) {
                    delItem = el;
                    break;
                }
            }
            result = this.ask.remove(delItem);
        } else {
            for (Item el : ask) {
                if (el.getVolume() == item.getVolume()
                        && el.getPrice() == item.getPrice()) {
                    delItem = el;
                    break;
                }
            }
            result = this.bid.remove(delItem);
        }
        return result;
    }

    /**
     * Объединяет в заданном множестве все заявки
     * с одинаковой заданной ценой.
     * @param set множество
     * @return список с объединенными заявками
     */
    private ArrayList<Item> uniteItems(Set<Item> set) {
        double price = 0;
        ArrayList<Item> list = new ArrayList<>();
        boolean start = true;
        Item samePrice = null;
        for (Item item : set) {
            if (start) {
                samePrice = new Item(this.name, true, false, item.getPrice(), item.getVolume());
                price = item.getPrice();
                start = false;
            } else if (item.getPrice() != price) {
                list.add(samePrice);
                samePrice = new Item(this.name, true, false, item.getPrice(), item.getVolume());
            } else {
                samePrice.setVolume(samePrice.getVolume() + item.getVolume());
            }
        }
        if (samePrice != null) {
            list.add(samePrice);
        }

        return list;
    }

    /**
     * Выводит стакан на экран
     * @return строка
     */
    @Override
    public String toString() {
        ArrayList<Item> unitedAsks = uniteItems(ask);
        ArrayList<Item> unitedBids = uniteItems(bid);
        Map<Double, Integer[]> priceMap = new HashMap<>();
        for (Item item : unitedAsks) {
            Integer[] array = new Integer[2];
            array[1] = item.getVolume();
            priceMap.put(item.getPrice(), array);
        }
        for (Item item : unitedBids) {
            if (priceMap.containsKey(item.getPrice())) {
                Integer[] array = priceMap.get(item.getPrice());
                array[0] = item.getVolume();
                priceMap.put(item.getPrice(), array);
            } else {
                Integer[] array = new Integer[2];
                array[0] = item.getVolume();
                priceMap.put(item.getPrice(), array);
            }
        }

        StringBuilder string = new StringBuilder();

        for (Double price : priceMap.keySet()) {
            Integer bid = priceMap.get(price)[0];
            Integer ask = priceMap.get(price)[1];
            string.append(String.format("%s     %s     %s", bid, price, ask))
            .append("\n");
        }
        return string.toString();
    }
}
