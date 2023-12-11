package concurrency;

public class BankBalanceWithoutConcurrency implements BankBalance{
    private double availableBalance;

    public BankBalanceWithoutConcurrency(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void deposit(double amount) {
        double tempAvailableBalance = availableBalance;
        availableBalance += amount;
        System.out.println("Previous balance: " + tempAvailableBalance + " Deposit amount: " + amount + " Current Balance: " + availableBalance);
    }
    public void withdraw(double amount) {
        double tempAvailableBalance = availableBalance;
        availableBalance -= amount;
        System.out.println("Previous balance: " + tempAvailableBalance + " Withdraw amount: " + amount + " Current Balance: " + availableBalance);
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}
