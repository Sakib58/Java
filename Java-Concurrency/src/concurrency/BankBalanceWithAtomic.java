package concurrency;

import java.util.concurrent.atomic.DoubleAdder;

public class BankBalanceWithAtomic implements BankBalance{
    private DoubleAdder availableBalance;

    public BankBalanceWithAtomic(double availableBalance) {
        this.availableBalance = new DoubleAdder();
        this.availableBalance.add(availableBalance);
    }

    public void deposit(double amount) {
        DoubleAdder tempAvailableBalance = availableBalance;
        availableBalance.add(amount);
        System.out.println("Previous balance: " + tempAvailableBalance + " Deposit amount: " + amount + " Current Balance: " + availableBalance);
    }
    public void withdraw(double amount) {
        DoubleAdder tempAvailableBalance = availableBalance;
        availableBalance.add(-amount);
        System.out.println("Previous balance: " + tempAvailableBalance + " Withdraw amount: " + amount + " Current Balance: " + availableBalance);
    }

    public double getAvailableBalance() {
        return availableBalance.doubleValue();
    }
}
