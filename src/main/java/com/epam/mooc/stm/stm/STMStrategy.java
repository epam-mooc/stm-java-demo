package com.epam.mooc.stm.stm;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class STMStrategy implements AccountStrategy {
    @Override
    public String name() {
        return "STM";
    }

    @Override
    public Bank createBank(Account[] accounts) {
        return new STMBank(accounts);
    }

    @Override
    public Account createAccount(long initialBalance) {
        return new STMAccount(initialBalance);
    }
}
