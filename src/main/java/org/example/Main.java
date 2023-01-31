package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue:
 * Класс Продьюсер - генерирует сообщения в очередь
 * Класс Консьюмер - забирает сообщения из очереди
 * Задача - реализовать класс Consumer, который будет анализировать сообщения в очереди и
 * при входящем сообщении "exit" заканчивать работу. При реализации можно использовать класс BlockingQueue
 * в репозитории или одну из стандартных реализаций BlockingQueue из библиотеки.
 */
public class Main {
    public static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList(
                "Floor0", "Floor1", "Floor2", "Floor3", "Floor4", "Floor5", "Floor6", "exit", "Floor7",
                "Floor8", "Floor9", "Floor10"));
        queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue, list);
        Consumer consumer = new Consumer(queue, producer);
        producer.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.start();
    }
}