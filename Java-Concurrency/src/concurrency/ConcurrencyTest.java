package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing concurrent operations.....");
        // BankBalance bankBalance = new BankBalanceWithoutConcurrency(1000);
        // BankBalance bankBalance = new BankBalanceWithSynchronized(1000);
        BankBalance bankBalance = new BankBalanceWithLock(1000);
        TransactionTask task1 = new TransactionTask(bankBalance, 100, "deposit");
        TransactionTask task2 = new TransactionTask(bankBalance, 70, "withdraw");
        TransactionTask task3 = new TransactionTask(bankBalance, 110, "deposit");
        TransactionTask task4 = new TransactionTask(bankBalance, 90, "withdraw");
        TransactionTask task5 = new TransactionTask(bankBalance, 50, "deposit");
        TransactionTask task6 = new TransactionTask(bankBalance, 60, "withdraw");
        TransactionTask task7 = new TransactionTask(bankBalance, 80, "deposit");
        TransactionTask task8 = new TransactionTask(bankBalance, 110, "withdraw");
        TransactionTask task9 = new TransactionTask(bankBalance, 50, "deposit");
        TransactionTask task10 = new TransactionTask(bankBalance, 140, "withdraw");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);
        executorService.submit(task4);
        executorService.submit(task5);
        executorService.submit(task6);
        executorService.submit(task7);
        executorService.submit(task8);
        executorService.submit(task9);
        executorService.submit(task10);

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            Thread.sleep(2000);
        }
        System.out.println("Balance after all transaction: " + bankBalance.getAvailableBalance());
    }
}
