package concurrency;


import java.util.concurrent.locks.ReentrantLock;

public class BankBalanceWithLock implements BankBalance{
    private ReentrantLock lock;
    private double availableBalance;

    public BankBalanceWithLock(double availableBalance) {
        lock = new ReentrantLock();
        this.availableBalance = availableBalance;
    }

    public void deposit(double amount) {
        try {
            lock.lock();
            double tempAvailableBalance = availableBalance;
            availableBalance += amount;
            System.out.println("Previous balance: " + tempAvailableBalance + " Deposit amount: " + amount + " Current Balance: " + availableBalance);
        } finally {
            lock.unlock();
        }
    }
    public void withdraw(double amount) {
        try {
            lock.lock();
            double tempAvailableBalance = availableBalance;
            availableBalance -= amount;
            System.out.println("Previous balance: " + tempAvailableBalance + " Withdraw amount: " + amount + " Current Balance: " + availableBalance);
        } finally {
            lock.unlock();
        }
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}
