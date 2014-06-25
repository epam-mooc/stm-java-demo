package com.epam.mooc.stm.interfaces;

/**
 * @author mishadoff
 */
public interface AccountTransfer {
    void transfer(Account a1, Account a2, long amount);
    Account[] accounts();
}
