package com.epam.mooc.stm.sync_account;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class SyncAccountStrategy implements AccountStrategy {
    @Override
    public Account createAccount(long initialBalance) {
        return new SyncAccountAccount(initialBalance);
    }

    @Override
    public Bank createBank(Account[] accounts) {
        return new SyncAccountBank(accounts);
    }

    @Override
    public String name() {
        return "SYNC_ACCOUNT";
    }
}
