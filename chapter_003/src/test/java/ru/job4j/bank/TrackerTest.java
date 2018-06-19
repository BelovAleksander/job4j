package ru.job4j.bank;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 20.06.18
 */
public class TrackerTest {
    @Test
    public void whenAddUserThenSuccess() {
        Tracker tracker = new Tracker();
        User user = new User("alex", "123");
        tracker.addUser(user);
        boolean result = tracker.bank.containsKey(user);
        assertThat(result, is(true));
    }

    @Test
    public void whenDeleteUserThenSuccess() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "123");
        User petya = new User("petya", "1234");
        tracker.addUser(alex);
        tracker.addUser(petya);
        tracker.deleteUser(alex);
        int result = tracker.bank.keySet().size();
        assertThat(result, is(1));
    }

    @Test
    public void whenAddAccountToUserThenSuccess() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "123");
        tracker.addUser(alex);
        tracker.addAccountToUser("123", new Account(100, "1234"));
        String result = alex.getAccounts().get(0).getRequisites();
        assertThat(result, is("1234"));
    }

    @Test
    public void whenDeleteAccountFromUserThenSuccess() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "123");
        tracker.addUser(alex);
        Account test = new Account(100, "1234");
        tracker.addAccountToUser("123", test);
        tracker.addAccountToUser("123", new Account(200, "12345"));
        tracker.deleteAccountFromUser("123", test);
        double result = alex.getAccounts().get(0).getValue();
        assertThat(result, is(200D));
    }

    @Test
    public void whenGetUserAccountsThenListSizeIs2() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "123");
        tracker.addUser(alex);
        Account test = new Account(100, "1234");
        tracker.addAccountToUser("123", test);
        tracker.addAccountToUser("123", new Account(200, "12345"));
        int result = tracker.getUserAccounts("123").size();
        assertThat(result, is(2));
    }

    @Test
    public void whenTransfer100FromUser1ToUser2ThenSuccess() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "srcPas");
        tracker.addUser(alex);
        Account test = new Account(100, "srcReq");
        tracker.addAccountToUser("srcPas", test);
        tracker.addAccountToUser("srcPas", new Account(200, "12345"));
        User petya = new User("petya", "dstPas");
        Account test2 = new Account(50, "dstReq");
        tracker.addUser(petya);
        tracker.addAccountToUser("dstPas", test2);
        tracker.transferMoney("srcPas", "srcReq",
                "dstPas", "dstReq", 100);
        int[] result = new int[]{(int) tracker.searchAccount("srcReq").getValue(),
                (int) tracker.searchAccount("dstReq").getValue()};
        assertThat(result, is(new int[]{0, 150}));
    }

    @Test
    public void whenNotEnoughMoneyThenTransferFalse() {
        Tracker tracker = new Tracker();
        User alex = new User("alex", "srcPas");
        tracker.addUser(alex);
        Account test = new Account(10, "srcReq");
        tracker.addAccountToUser("srcPas", test);
        tracker.addAccountToUser("srcPas", new Account(200, "12345"));
        User petya = new User("petya", "dstPas");
        Account test2 = new Account(50, "dstReq");
        tracker.addUser(petya);
        tracker.addAccountToUser("dstPas", test2);
        boolean result = tracker.transferMoney("srcPas", "srcReq",
                "dstPas", "dstReq", 100);
        assertThat(result, is(false));
    }
}
