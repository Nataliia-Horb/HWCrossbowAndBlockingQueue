package org.example;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
    BlockingQueue<String> queue;
    Thread producer;

    public Consumer(BlockingQueue<String> blockingQueue, Thread producer) {
        this.queue = blockingQueue;
        this.producer = producer;
    }

    @Override
    public void run() {
        String message;
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
                message = queue.take();
                System.out.println("-" + message);
                if ("exit".equals(message)) {
                    producer.interrupt();
                    System.out.println("Threads stopped by the consumer");
                    return;
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
