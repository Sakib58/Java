package concurrency;

public class BankBalanceWithSynchronized implements BankBalance{
    private double availableBalance;

    public BankBalanceWithSynchronized(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public synchronized void deposit(double amount) {
        double tempAvailableBalance = availableBalance;
        availableBalance += amount;
        System.out.println("Previous balance: " + tempAvailableBalance + " Deposit amount: " + amount + " Current Balance: " + availableBalance);
    }
    public synchronized void withdraw(double amount) {
        double tempAvailableBalance = availableBalance;
        availableBalance -= amount;
        System.out.println("Previous balance: " + tempAvailableBalance + " Withdraw amount: " + amount + " Current Balance: " + availableBalance);
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}
