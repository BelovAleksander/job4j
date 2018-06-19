package ru.job4j.bank;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String passport;
    private List<Account> accounts = new ArrayList<>();

    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        System.out.println("Account added. Passport: " + this.passport);
    }

    public void deleteAccount(Account account) {
        if (!accounts.contains(account)) {
            System.out.println("There is no such account!");
        }
        accounts.remove(account);
        System.out.println("Account deleted.");
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public String getPassport() {
        return this.passport;
    }

    @Override
    public boolean equals(Object o) {
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
