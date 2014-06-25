package com.epam.mooc.stm.stm;

import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.Bank;
import org.multiverse.api.StmUtils;

/**
 * @author mishadoff
 */
public class STMBank implements Bank {
    private Account[] accounts;

    public STMBank(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public void transfer(final Account a1, final Account a2, final long amount) {
        StmUtils.atomic(new Runnable() {
            @Override
            public void run() {
                if (a1.balance() >= amount) {
                    a1.incBalance(-amount);
                    a2.incBalance(amount);
                }
            }
        });
    }

    @Override
    public Account[] accounts() {
        return accounts;
    }
}
