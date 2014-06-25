package com.epam.mooc.stm.baseline;

import com.epam.mooc.stm.interfaces.Account;

/**
 * @author mishadoff
 */
public class BaselineAccount implements Account {

    private long balance;

    public BaselineAccount(long initialBalance) {
        balance = initialBalance;
    }

    @Override
    public long balance() {
        return balance;
    }

    @Override
    public void incBalance(long amount) {
        balance += amount;
    }
}
