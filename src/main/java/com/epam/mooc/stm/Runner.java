package com.epam.mooc.stm;

import com.epam.mooc.stm.baseline.BaselineStrategy;
import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.TransactionThread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mishadoff
 */
public class Runner {

    public final static int NUMBER_OF_ACCOUNTS = 1000;
    public final static int NUMBER_OF_THREADS = 2;
    public final static long NUMBER_OF_TRANSACTIONS = 10000000;


    public static void main(String[] args) throws Exception {

        // CHOOSE YOUR DESTINY^W STRATEGY
        AccountStrategy strategy = new BaselineStrategy();


        final Account[] accounts = new Account[NUMBER_OF_ACCOUNTS];

        Random random = new Random();
        // filling accounts with random values
        for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
            accounts[i] = strategy.createAccount(10000);
        }

        // sum before transactions
        System.out.println("Sum before: " + sum(accounts));

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        long before = System.currentTimeMillis();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            service.submit(new TransactionThread(strategy.createAccountTransaction(accounts)));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        long after = System.currentTimeMillis();
        System.out.println("Sum  after: " + sum(accounts));
        System.out.println("Time elapsed: " + (after - before) + " ms");

    }

    private static long sum(Account[] accounts) {
        long sum = 0;
        for (Account account : accounts) {
            sum += account.balance();
        }
        return sum;
    }
}
