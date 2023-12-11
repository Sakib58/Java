package basics;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciForkJoinTask extends RecursiveTask<Long> {
    private final int n;
    private static final int THRESHOLD = 10;

    public FibonacciForkJoinTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return (long) n;
        }

        if (n <= THRESHOLD) {
            return FibonacciForkJoinTask.calculateFibonacci(n); // doing that for non-parallel
        }

        FibonacciForkJoinTask fib1 = new FibonacciForkJoinTask(n - 1);
        FibonacciForkJoinTask fib2 = new FibonacciForkJoinTask(n - 2);

        fib1.fork();
        fib2.fork();
        return fib1.join() + fib2.join();
    }

    private static long calculateFibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int n = 45;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long result = forkJoinPool.invoke(new FibonacciForkJoinTask(n));
        System.out.println("The " + n + "th Fibonacci number is: " + result);
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1F;
        System.out.println(sec + " milliseconds");
    }
}
