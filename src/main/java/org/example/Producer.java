package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    BlockingQueue<String> queue;
    List<String> list;

    public Producer(BlockingQueue<String> blockingQueue, List<String> list) {
        this.queue = blockingQueue;
        this.list = list;
    }

    @Override
    public void run() {
        String message;
        while (!Thread.interrupted()) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    Thread.sleep(1000);
                    message = list.get(i);
                    System.out.println("+" + message);
                    queue.put(message);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}

