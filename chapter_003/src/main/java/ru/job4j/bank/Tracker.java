package ru.job4j.bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker {
    Map<User, List<Account>> bank = new HashMap<>();

    public final void addUser(final User user) {
        this.bank.putIfAbsent(user, user.getAccounts());
    }

    public final void deleteUser(final User user) {
        this.bank.remove(user);
    }

    public final void addAccountToUser(final String passport, final Account account) {
        for (User user : this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                user.addAccount(account);
                break;
            }
        }
    }

    public final void deleteAccountFromUser(final String passport, final Account account) {
        for (User user : this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                user.deleteAccount(account);
                break;
            }
        }
    }

    public final List<Account> getUserAccounts(final String passport) {
        List<Account> result = null;
        for (User user : this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                result = user.getAccounts();
                break;
            }
        }
        return result;
    }

    public final boolean transferMoney(final String srcPassport, final String srcRequisite,
                                       final String dstPassport, final String dstRequisite,
                                       final double amount) {
        boolean result = false;
        User sender = null;
        User recipient = null;
        for (User user : bank.keySet()) {
            if (user.getPassport().equals(srcPassport)) {
                sender = user;
            } else if (user.getPassport().equals(dstPassport)) {
                recipient = user;
            } else if (sender != null && recipient != null) {
                break;
            }
        }
        Account src = null;
        for (Account account : sender.getAccounts()) {
            if (account.getRequisites().equals(srcRequisite)
                    && account.getValue() >= amount) {
                src = account;
            }
        }
        for (Account account : recipient.getAccounts()) {
            if (account.getRequisites().equals(dstRequisite)
                    && src != null) {
                src.changeValue(-amount);
                account.changeValue(amount);
                result = true;
            }
        }
        if (result) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed!");
        }
        return result;
    }
}
