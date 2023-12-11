package basics;

import java.util.List;

public class Consumer implements Runnable {
    private List<Integer> consumedStream;
    public Consumer(List<Integer> consumedStream) {
        this.consumedStream = consumedStream;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            while (consumedStream.size()>0)
            {
                try
                {
                    Thread.sleep(50);
                    System.out.println("Receiving integer: " + consumedStream.get(0) + " From: " + Thread.currentThread().getName());
                    consumedStream.remove(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
