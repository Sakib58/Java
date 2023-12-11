package concurrency;

public class TransactionTask implements Runnable{
    private BankBalance bankBalance;
    private double transactionAmount;
    private String transactionType;

    public TransactionTask(BankBalance bankBalance, double transactionAmount, String transactionType) {
        this.bankBalance = bankBalance;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            if (transactionType.equals("deposit")) {
                bankBalance.deposit(transactionAmount);
            }
            else if (transactionType.equals("withdraw")) {
                bankBalance.withdraw(transactionAmount);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
