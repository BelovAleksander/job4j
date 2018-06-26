package ru.job4j.generic;

/**
 * Класс-шаблон для создания классов UserStore и RoleStore.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.06.18
 */
public abstract class AbstractStore implements Store {
    /**
     * база данных
     */
    private DynamicArray<Base> array;
    /**
     * заданный размер массива
     */
    private int size;

    public AbstractStore(int size) {
        this.array = new DynamicArray<>(size);
        this.size = size;
    }

    /**
     * Добавление нового элемента.
     * @param model новый элемент
     */
    @Override
    public void add(Base model) {
        this.array.add(model);
    }

    /**
     * Поиск по id и замена на новый заданный элемент.
     * @param id id
     * @param model новый элемент
     * @return true, если успешно
     */
    @Override
    public boolean replace(String id, Base model) {
        boolean result = false;
        int index = findIndex(id);
        if (index != -1) {
            this.array.set(index, model);
            result = true;
        }
        return result;
    }

    /**
     * Удаление элемента
     * @param id id удаляемого элемента
     * @return true, если успешно
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        int index = findIndex(id);
        if (index != -1) {
            this.array.delete(index);
            result = true;
        }
        return result;
    }

    /**
     * Поиск элемента по id.
     * @param id id
     * @return null, если неудачно
     */
    @Override
    public Base findById(String id) {
        int result = findIndex(id);
        return result != -1 ? this.array.get(result) : null;
    }

    /**
     * Поиск индекса в массиве по id.
     * @param id id
     * @return индекс в массиве
     */
    public int findIndex(String id) {
        int result = -1;
        for (int index = 0; index < this.size; index++) {
            if (this.array.get(index) != null
                    && this.array.get(index).getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
