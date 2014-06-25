package com.epam.mooc.stm.sync_bank;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class SyncBankBank implements Bank {
    private Account[] accounts;

    public SyncBankBank(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public synchronized void transfer(Account a1, Account a2, long amount) {
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