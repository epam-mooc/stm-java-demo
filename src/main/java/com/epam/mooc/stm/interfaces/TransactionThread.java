package com.epam.mooc.stm.interfaces;

import com.epam.mooc.stm.Runner;

import java.util.Random;

/**
 * @author mishadoff
 */
public class TransactionThread implements Runnable {
    private AccountTransfer bank;
    private Random random = new Random();
    private Account[] accounts;

    public TransactionThread(AccountTransfer bank) {
        this.bank = bank;
        this.accounts = bank.accounts();
    }

    @Override
    public void run() {
        for (int i = 0; i < Runner.NUMBER_OF_TRANSACTIONS; i++) {
            int accountIndex1 = random.nextInt(accounts.length);
            int accountIndex2 = random.nextInt(accounts.length);
            bank.transfer(accounts[accountIndex1], accounts[accountIndex2], random.nextInt(1000));
        }
    }
}
