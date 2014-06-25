package com.epam.mooc.stm.baseline;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.AccountTransfer;

/**
 * @author mishadoff
 */
public class BaselineStrategy implements AccountStrategy {
    @Override
    public Account createAccount(long initialBalance) {
        return new BaselineAccount(initialBalance);
    }

    @Override
    public AccountTransfer createAccountTransaction(Account[] accounts) {
        return new BaselineTransfer(accounts);
    }
}
