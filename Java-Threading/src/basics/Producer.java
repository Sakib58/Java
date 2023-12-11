package basics;

import java.util.List;

public class Producer extends Thread{
    private List<Integer> produceStream;

    public Producer(List<Integer> produceStream) {
        this.produceStream = produceStream;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                produceStream.add(produceStream.size()+1);
                System.out.println("Streaming int: " +(produceStream.size()+1) +" from: " +getName());
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
