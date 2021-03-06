package ru.job4j.bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отображает набор доступных операций.
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 20.06.18
 */
public class Tracker {
    /**
     * база данных счетов с привязкой к пользователям.
     */
    Map<User, List<Account>> bank = new HashMap<>();
    /**
     * Добавление пользователя.
     * @param user пользователь
     */
    public final void addUser(final User user) {
        this.bank.putIfAbsent(user, user.getAccounts());
    }

    /**
     * Удаление пользователя.
     * @param user пользователь
     */
    public final void deleteUser(final User user) {
        this.bank.remove(user);
    }

    /**
     * Открытие нового счета
     * @param passport id пользователя
     * @param account счет
     */
    public final void addAccountToUser(final String passport, final Account account) {
        for (User user : this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                user.addAccount(account);
                break;
            }
        }
    }

    /**
     * Удаление счета.
     * @param passport id пользователя
     * @param account счет
     */
    public final void deleteAccountFromUser(final String passport, final Account account) {
        for (User user : this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                user.deleteAccount(account);
                break;
            }
        }
    }

    /**
     * Все счета пользователя.
     * @param passport id пользователя
     * @return список счетов
     */
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

    /**
     * Перевод средств.
     * @param srcPassport id отправителя
     * @param srcRequisite реквизиты счета отправителя
     * @param dstPassport id получателя
     * @param dstRequisite реквизиты счета получателя
     * @param amount средства
     * @return успех перевода
     */
    public final boolean transferMoney(final String srcPassport, final String srcRequisite,
                                       final String dstPassport, final String dstRequisite,
                                       final double amount) {
        boolean result = false;
        Account src = findAccount(srcRequisite, getUserAccounts(srcPassport));
        Account dst = findAccount(dstRequisite, getUserAccounts(dstPassport));
        if (src != null && dst != null && src.getValue() >= amount) {
            src.changeValue(-amount);
            dst.changeValue(amount);
            result = true;
            System.out.println("Transfer succeed!");
        } else {
            System.out.println("Transfer failed!");
        }
        return result;
    }
    public Account findAccount(String requisite, List<Account> list) {
        Account result = null;
        for (Account account : list) {
            if (account.getRequisites().equals(requisite)) {
                result = account;
            }
        }
        return result;
    }
}
