package basics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        List<Integer> streamInts = new ArrayList<>();
        Thread producer = new Producer(streamInts);
        Thread consumer = new Thread(new Consumer(streamInts));
//        producer.start();
//        consumer.start();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        System.out.println("Producer is starting......");
//        Future futureProducer = executorService.submit(producer);
        executorService.scheduleAtFixedRate(producer, 0, 100, TimeUnit.MILLISECONDS);
        System.out.println("Consumer is starting......");
//        Future futureConsumer = executorService.submit(consumer);
        executorService.scheduleAtFixedRate(consumer, 10, 20, TimeUnit.MILLISECONDS);
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
            executorService.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
