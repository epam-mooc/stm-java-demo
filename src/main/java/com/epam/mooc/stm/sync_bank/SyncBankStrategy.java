package com.epam.mooc.stm.sync_bank;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class SyncBankStrategy implements AccountStrategy {
    @Override
    public Account createAccount(long initialBalance) {
        return new SyncBankAccount(initialBalance);
    }

    @Override
    public Bank createBank(Account[] accounts) {
        return new SyncBankBank(accounts);
    }

    @Override
    public String name() {
        return "SYNC_BANK";
    }
}
