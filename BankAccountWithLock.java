package week8;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

public class BankAccountWithLock {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    // Read balance (shared lock)
    public double getBalance() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reads balance: " + balance);
            return balance;
        } finally {
            readLock.unlock();
        }
    }

    // Deposit money (exclusive lock)
    public void deposit(double amount) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " deposits: " + amount);
            balance += amount;
        } finally {
            writeLock.unlock();
        }
    }

    // Withdraw money (exclusive lock)
    public void withdraw(double amount) {
        writeLock.lock();
        try {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " withdraws: " + amount);
                balance -= amount;
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds for: " + amount);
            }
        } finally {
            writeLock.unlock();
        }
    }

    // Main class (Question a)
    public static void main(String[] args) {
        BankAccountWithLock account = new BankAccountWithLock(1000.0);

        // Deposit thread
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(200);
                try { Thread.sleep(100); }
                catch (InterruptedException e) {}
            }
        }, "DepositThread").start();

        // Withdraw thread
        new Thread(() -> {
            account.withdraw(500);
            account.withdraw(1500);
        }, "WithdrawThread").start();

        // Read balance thread
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                account.getBalance();
                try { Thread.sleep(50); }
                catch (InterruptedException e) {}
            }
        }, "ReadThread").start();
    }
}