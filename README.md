# stm-java-demo

Java CLI to demonstrate different strategies for concurrency

## Setup

Before you start, make sure you have `java` and `maven` installed

1. Build executable jar

    ```
    mvn package
    ```

2. Run jar

    ```
    java -jar stm-java-demo.jar
    ```

3. Play with options
   - `--strategy=BASELINE`
   - `--threads=2`
   - `--accounts=100`
   - `--nPerThread`=100000`


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

`Bank` has access to all accounts and implements a transfer logic between accounts

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