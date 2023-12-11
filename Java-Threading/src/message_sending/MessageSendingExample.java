package message_sending;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MessageSendingExample {

    public static void main(String[] args) throws InterruptedException {
        int totalUsers = 100;
//        CountDownLatch countDownLatch = new CountDownLatch(totalUsers);

        List<String> userContactNumbers = generateUserContactNumbers(totalUsers);

        // Create a thread pool with a maximum of 10 threads, and it is customizable
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // List to store Future objects representing the status of each message sending task
        List<Future<MessageSendingResult>> futures = new ArrayList<>();

        for (String contactNumber : userContactNumbers) {
            MessageSendingTask task = new MessageSendingTask(contactNumber, "Hello, this is your message!");
            Future<MessageSendingResult> future = executorService.submit(task);
            futures.add(future);
        }
//        countDownLatch.await();
//        System.out.println("\n................................................\nSending Message to all contacts done!");

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        processMessageSendingResults(futures);
    }

    private static List<String> generateUserContactNumbers(int totalUsers) {
        List<String> userContactNumbers = new ArrayList<>();
        for (int i = 1; i <= totalUsers; i++) {
            userContactNumbers.add("User" + i);
        }
        return userContactNumbers;
    }

    private static void processMessageSendingResults(List<Future<MessageSendingResult>> futures) {
        for (Future<MessageSendingResult> future : futures) {
            try {
                MessageSendingResult result = future.get();
                if (result.success()) {
                    System.out.println("Message sent successfully to user: " + result.contactNumber());
                } else {
                    System.out.println("Failed to send message to user: " + result.contactNumber() +
                            ". Error: " + result.error());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
