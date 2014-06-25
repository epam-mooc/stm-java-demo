package com.epam.mooc.stm.stm;

import com.epam.mooc.stm.Runner;
import com.epam.mooc.stm.interfaces.AccountTransfer;

import java.util.Random;

/**
 * @author mishadoff
 */
public class StmAccountTransaction implements AccountTransfer {
    private final Account[] accounts;
    private final Random random = new Random();

    public StmAccountTransaction(Account[] accounts) {
        this.accounts = accounts;
    }

    //@Override
    public void run() {
        for (int i = 0; i < Runner.NUMBER_OF_TRANSACTIONS; i++) {
            int accountIndex1 = random.nextInt(accounts.length);
            int accountIndex2 = random.nextInt(accounts.length);
           // transfer(accounts[accountIndex1], accounts[accountIndex2], random.nextInt(1000));
        }
    }

    @Override
    public void transfer(com.epam.mooc.stm.interfaces.Account a1, com.epam.mooc.stm.interfaces.Account a2, long amount) {

    }

    @Override
    public com.epam.mooc.stm.interfaces.Account[] accounts() {
        return new com.epam.mooc.stm.interfaces.Account[0];
    }
}
