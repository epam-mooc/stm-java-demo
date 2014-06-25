package com.epam.mooc.stm.stm;

import com.epam.mooc.stm.interfaces.Account;
import org.multiverse.api.StmUtils;
import org.multiverse.api.functions.Function;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;
import org.multiverse.api.references.TxnRef;

import java.math.BigDecimal;

/**
 * @author mishadoff
 */
public class STMAccount implements Account {
    private final TxnLong balance;

    public STMAccount(long init) {
        this.balance = StmUtils.newTxnLong(init);
    }

    @Override
    public long balance() {
        return balance.atomicGet();
    }

    @Override
    public void incBalance(final long amount) {
        StmUtils.atomic(new Runnable() {
            @Override
            public void run() {
                balance.set(balance.get() + amount);
            }
        });
    }
}
