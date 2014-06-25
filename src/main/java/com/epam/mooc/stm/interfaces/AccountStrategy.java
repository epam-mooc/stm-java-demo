package com.epam.mooc.stm.interfaces;

/**
 * @author mishadoff
 */
public interface AccountStrategy {
    Account createAccount(long initialBalance);
    Bank createBank(Account[] accounts);

    // system
    String name();
}
