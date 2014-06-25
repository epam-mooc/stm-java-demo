package com.epam.mooc.stm.baseline;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.Bank;

/**
 * @author mishadoff
 */
public class BaselineStrategy implements AccountStrategy {
    @Override
    public Account createAccount(long initialBalance) {
        return new BaselineAccount(initialBalance);
    }

    @Override
    public Bank createBank(Account[] accounts) {
        return new BaselineTransfer(accounts);
    }

    @Override
    public String name() {
        return "BASELINE";
    }
}
