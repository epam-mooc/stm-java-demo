package com.epam.mooc.stm.sync_bank;

import com.epam.mooc.stm.interfaces.Account;

/**
 * @author mishadoff
 */
public class SyncBankAccount implements Account {
    private long balance;

    public SyncBankAccount(long balance) {
        this.balance = balance;
    }

    @Override
    public void incBalance(long amount) {
        balance += amount;
    }

    @Override
    public long balance() {
        return balance;
    }
}
