package com.epam.mooc.stm;

import com.epam.mooc.stm.baseline.BaselineStrategy;
import com.epam.mooc.stm.interfaces.Account;
import com.epam.mooc.stm.interfaces.AccountStrategy;
import com.epam.mooc.stm.interfaces.Bank;
import com.epam.mooc.stm.interfaces.TransactionThread;
import com.epam.mooc.stm.stm.STMStrategy;
import com.epam.mooc.stm.sync_account.SyncAccountStrategy;
import com.epam.mooc.stm.sync_bank.SyncBankStrategy;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mishadoff
 */
public class Runner {

    public static int NUMBER_OF_ACCOUNTS = 1000;
    public static int NUMBER_OF_THREADS = 10;
    public static long NUMBER_OF_TRANSACTIONS_PER_THREAD = 10000000;

    // CHOOSE YOUR DESTINY^W STRATEGY
    static AccountStrategy strategy = new BaselineStrategy();

    static HashMap<String, AccountStrategy> strategies = new HashMap<String, AccountStrategy>();
    static {
        for (AccountStrategy s : Arrays.asList(
                new BaselineStrategy(),
                new SyncBankStrategy(),
                new STMStrategy(),
                new SyncAccountStrategy())) {
            strategies.put(s.name(), s);
        }
    }

    public static void main(String[] args) throws Exception {

        parseArguments(args);

        final Account[] accounts = new Account[NUMBER_OF_ACCOUNTS];

        Random random = new Random();
        // filling accounts with random values
        for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
            accounts[i] = strategy.createAccount(10000);
        }

        System.out.println("[INFO] Evaluation started...");

        // sum before transactions
        long sumBefore = sum(accounts);
        System.out.println("[INFO] Sum before: " + sumBefore);

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        long before = System.currentTimeMillis();

        Bank bank = strategy.createBank(accounts);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            service.submit(new TransactionThread(bank));
        }
        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);

        long after = System.currentTimeMillis();
        long sumAfter = sum(accounts);
        System.out.println("[INFO] Sum after: " + sumAfter);
        System.out.println("[INFO] Delta: " + Math.abs(sumAfter - sumBefore));
        System.out.println("[INFO] Finished. Time elapsed: " + (after - before) + " ms");

    }

    private static void parseArguments(String[] args) {
        // create the command line parser
        CommandLineParser parser = new GnuParser();

        // create the Options
        Options options = new Options();

        options.addOption("strategy", true, "Available strategies: baseline");
        options.addOption("threads", true, "Number of threads [1..1_000]");
        options.addOption("accounts", true, "Number of accounts [1..1_000_000]");
        options.addOption("nPerThread", true, "Number of transactions per thread [1..1_000_000_000]");

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // validate number of threads
            if(line.hasOption("threads")) {
                String numOfStr = line.getOptionValue("threads");
                try {
                    int num = Integer.parseInt(numOfStr);
                    if (num < 1 || num > 1000) throw new NumberFormatException("Should be in [1..1_000]");
                    NUMBER_OF_THREADS = num;
                } catch (NumberFormatException nfe) {
                    System.out.println("[WARN] " + numOfStr + " is not valid option. Using default=" + NUMBER_OF_THREADS);
                }
            }

            // validate number of accounts
            if(line.hasOption("accounts")) {
                String numOfStr = line.getOptionValue("accounts");
                try {
                    int num = Integer.parseInt(numOfStr);
                    if (num < 1 || num > 1000000) throw new NumberFormatException("Should be in [1..1_000_000]");
                    NUMBER_OF_ACCOUNTS = num;
                } catch (NumberFormatException nfe) {
                    System.out.println("[WARN] " + numOfStr + " is not valid option. Using default=" + NUMBER_OF_ACCOUNTS);
                }
            }

            // validate number of transactions per thread
            if(line.hasOption("nPerThread")) {
                String numOfStr = line.getOptionValue("nPerThread");
                try {
                    long num = Long.parseLong(numOfStr);
                    if (num < 1 || num > 1000000000) throw new NumberFormatException("Should be in [1..1_000_000_000]");
                    NUMBER_OF_TRANSACTIONS_PER_THREAD = num;
                } catch (NumberFormatException nfe) {
                    System.out.println("[WARN] " + numOfStr + " is not valid option. Using default=" + NUMBER_OF_TRANSACTIONS_PER_THREAD);
                }
            }

            // validate number of transactions per thread
            if(line.hasOption("strategy")) {
                String numOfStr = line.getOptionValue("strategy").toUpperCase();
                if (strategies.containsKey(numOfStr)) {
                    strategy = strategies.get(numOfStr);
                } else {
                    System.out.println("[WARN] Strategy " + numOfStr + " is not found. Using default=BASELINE");
                }
            }
        }
        catch( ParseException exp ) {
            System.out.println("[ERROR] Make sure arguments passed correctly: " + exp.getMessage());
            System.out.println("[WARN] Using default options for al params.");
        }
        System.out.println("[INFO] Configuration loaded.");
        System.out.println("[INFO] Number of threads: " + NUMBER_OF_THREADS);
        System.out.println("[INFO] Number of accounts: " + NUMBER_OF_ACCOUNTS);
        System.out.println("[INFO] Number of transactions per thread: " + NUMBER_OF_TRANSACTIONS_PER_THREAD);
        System.out.println("[INFO] Strategy: " + strategy.name());
    }

    private static long sum(Account[] accounts) {
        long sum = 0;
        for (Account account : accounts) {
            sum += account.balance();
        }
        return sum;
    }
}
