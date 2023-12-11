package message_sending;

import java.util.concurrent.Callable;

class MessageSendingTask implements Callable<MessageSendingResult> {
    private final String contactNumber;
    private final String message;
//    private CountDownLatch countDownLatch;

    public MessageSendingTask(String contactNumber, String message) {
        this.contactNumber = contactNumber;
        this.message = message;
//        this.countDownLatch = countDownLatch;
    }

    @Override
    public MessageSendingResult call() {
        boolean success = false;
        try {
            success = sendMessage(contactNumber, message);

            if (success) {
                System.out.println("Successful! Message send to " + contactNumber + " successfully!");
                return new MessageSendingResult(contactNumber, true, null);
            } else {
                System.out.println("Failure! Failed to send message to " + contactNumber);
                return new MessageSendingResult(contactNumber, false, "Failed to send message");
            }
        } finally {
//            countDownLatch.countDown();
        }
    }

    private boolean sendMessage(String contactNumber, String message) {
        System.out.println("Sending message to " + contactNumber +" ...........");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Simulate success for 90% of cases
        return Math.random() < 0.9;
    }
}