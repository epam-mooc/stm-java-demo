package com.epam.mooc.stm.sync_account;

import com.epam.mooc.stm.interfaces.Account;

/**
 * @author mishadoff
 */
public class SyncAccountAccount implements Account {
    private long balance;

    public SyncAccountAccount(long balance) {
        this.balance = balance;
    }

    @Override
    public synchronized void incBalance(long amount) {
        Thread.yield();
        balance += amount;
    }

    @Override
    public synchronized long balance() {
        return balance;
    }
}
