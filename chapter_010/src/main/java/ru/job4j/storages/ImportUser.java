package ru.job4j.storages;

/**
 * @author Aleksandr Belov (whiterabbit.nsk@gmail.com)
 * @since 26.12.2018
 */
public class ImportUser {
    private Storage storage;

    public ImportUser(final Storage storage) {
        this.storage = storage;
    }

    public void addUser(final User user) {
        this.storage.add(user);
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }
}
