package com.epam.mooc.stm.sync;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnLong;

/**
 * @author mishadoff
 */
public class Account {
    private long balance;

    public Account(long init) {
        this.balance = init;
    }

    public long balance() {
        return balance;
    }

    public synchronized void incBalance(final long amount) {
        balance += amount;
    }
}
