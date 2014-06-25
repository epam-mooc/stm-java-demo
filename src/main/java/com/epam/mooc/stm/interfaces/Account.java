package com.epam.mooc.stm.interfaces;

/**
 * @author mishadoff
 */
public interface Account {
    void incBalance(long amount);
    long balance();
}
