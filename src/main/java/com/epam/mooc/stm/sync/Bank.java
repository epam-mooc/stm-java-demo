package com.epam.mooc.stm.sync;

import org.multiverse.api.StmUtils;

/**
 * @author mishadoff
 */
public class Bank {
    public void transfer(final Account a1, final Account a2, final long amount) {
        if (a1.balance() >= amount) {
            a1.incBalance(-amount);
            a2.incBalance(amount);
        }
    }
}
