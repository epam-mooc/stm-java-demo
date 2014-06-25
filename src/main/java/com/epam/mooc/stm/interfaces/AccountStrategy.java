package com.epam.mooc.stm.interfaces;

/**
 * @author mishadoff
 */
public interface AccountStrategy {
    Account createAccount(long initialBalance);
    AccountTransfer createAccountTransaction(Account[] accounts);
}
