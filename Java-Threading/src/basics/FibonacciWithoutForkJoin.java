package basics;

class FibonacciWithoutForkJoin {
    public static int calculateFibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
    }

    public static void main(String args[])
    {
        long start = System.currentTimeMillis();
        int n = 45;
        System.out.println("Sum of Fibonacci" +
                " numbers is : "+ calculateFibonacci(n));
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1F;
        System.out.println(sec + " milliseconds");
    }
}