# stm-java-demo

Java CLI to demonstrate different strategies for concurrency

**TODO** Rerun all experiments on 8-processor machine for better effect.

## Setup

Before you start, make sure you have `java` and `maven` installed

1. **TODO** Build executable jar

    ```
    mvn package
    ```

2. **TODO** Run jar

    ```
    java -jar stm-java-demo.jar
    ```

3. Play with options
   - `--strategy=BASELINE`
   - `--threads=2`
   - `--accounts=100`
   - `--nPerThread=100000`


## Problem description

The problem solved in this demo is **transfer funds between accounts**.

We consider just two metrics:
 - Delta between whole balance before and after
 - Elapsed time

## Interfaces

General approach is based on following interfaces.

`AccountStrategy` factory creates `Account` and `Bank`

``` java
public interface AccountStrategy {
    Account createAccount(long initialBalance);
    Bank createBank(Account[] accounts);
}
```

`Account` could increment its balance by value and get a current anount.

``` java
public interface Account {
    void incBalance(long amount);
    long balance();
}
```

`Bank` is shared object and has access to all accounts and implements a transfer logic between accounts

``` java
public interface Bank {
    void transfer(Account a1, Account a2, long amount);
    Account[] accounts();
}
```

`TransactionThread` just wraps bank logic in a thread and runs it multiple times.

## Available strategies

### Baseline

The simplest one. No synchronization involved.

Running it with 1 thread gives us great baseline time for a process

Options: `-strategy=baseline -threads=1 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: BASELINE
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 1674 ms
```

But if we add one more thread to simulation, balance consistency is broken


Options: `-strategy=baseline -threads=2 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: BASELINE
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10084680
[INFO] Delta: 84680
[INFO] Finished. Time elapsed: 2592 ms
```

### Sync on Account

Adding `synchronized` keyword on account level solves the problem.

Options: `-strategy=sync_account -threads=2 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: SYNC_ACCOUNT
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 4482 ms
```

Sure it takes more time as overhead for synchronization, but consistency is saved.
Let's reduce number of accounts to two.

Options: `-strategy=sync_account -threads=4 -accounts=2 -nPerThread=10000000`

```
[INFO] Strategy: SYNC_ACCOUNT
[INFO] Evaluation started...
[INFO] Sum before: 20000
[INFO] Sum after: 17687
[INFO] Delta: 2313
[INFO] Finished. Time elapsed: 300020 ms
```

What happened?

We encountered a deadlock and simulation never ends (it ends, actually, because of `service.awaitTermination(5, TimeUnit.MINUTES)`)
For better deadlock reproducability we added a `Thread.yield()` to `incBalance` method.

## Sync on Bank

To avoid deadlocks situation we could add `synchronized` on `transfer` method.
But our program becomes single-threaded. You won't get speed if you have a lot of processors.

Options: `-strategy=sync_bank -threads=10 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: SYNC_BANK
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 21799 ms
```

Test it on 100 threads.

Options: `-strategy=sync_bank -threads=100 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: SYNC_BANK
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 218227 ms
```

## STM

STM (Software Transactional Memory) by [multiverse implementation](http://multiverse.codehaus.org/overview.html)

Just wrap your critical sections to `StmUtils.atomic()`

10 threads

Options: `-strategy=stm -threads=10 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: STM
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 14066 ms
```

100 threads

Options: `-strategy=stm -threads=100 -accounts=1000 -nPerThread=10000000`

```
[INFO] Strategy: STM
[INFO] Evaluation started...
[INFO] Sum before: 10000000
[INFO] Sum after: 10000000
[INFO] Delta: 0
[INFO] Finished. Time elapsed: 19724 ms
```

It just works

![](http://www.mtfca.com/discus/messages/331880/359582.jpg)
