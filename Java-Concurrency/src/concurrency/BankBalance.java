package concurrency;

public interface BankBalance {
    public void deposit(double amount);
    public void withdraw(double amount);
    public double getAvailableBalance();
}
