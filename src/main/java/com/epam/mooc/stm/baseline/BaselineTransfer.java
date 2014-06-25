package com.epam.mooc.stm.baseline;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class BaselineTransfer implements Bank {
    private Account[] accounts;

    public BaselineTransfer(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public void transfer(Account a1, Account a2, long amount) {
        if (a1.balance() >= amount) {
            a1.incBalance(-amount);
            a2.incBalance(amount);
        }
    }

    @Override
    public Account[] accounts() {
        return accounts;
    }
}
