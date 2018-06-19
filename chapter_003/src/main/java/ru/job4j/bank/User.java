package ru.job4j.bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает Пользователя.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 20.06.18
 */
public class User {
    /**
     * имя
     */
    private final String name;
    /**
     * id
     */
    private final String passport;
    /**
     * список счетов, принадлежащих пользователю
     */
    private List<Account> accounts = new ArrayList<>();

    /**
     * Конструктор
     * @param name имя
     * @param passport id
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    /**
     * добавление счета
     * @param account
     */
    public final void addAccount(final Account account) {
        this.accounts.add(account);
        System.out.println("Account added. Passport: " + this.passport);
    }

    /**
     * удаление счета
     * @param account
     */
    public final void deleteAccount(final Account account) {
        if (!accounts.contains(account)) {
            System.out.println("There is no such account!");
        }
        accounts.remove(account);
        System.out.println("Account deleted.");
    }

    /**
     * геттер для списка счетов
     * @return список счетов
     */
    public final List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * геттер для id
     * @return id
     */
    public final String getPassport() {
        return this.passport;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        User user = (User) o;

        return this.name != null ? this.name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }
}
