package org.example;

import java.util.Scanner;

/**
 * Стрелок robinHood в одном потоке стреляет из арбалета,
 * его помощник servant в другом потоке приносит ему колчан, когда стрелы закончатся.
 * Заготовка логики в классе Crossbow. Доработать логику, чтобы:
 * <p>
 * стрельба продолжалась бесконечно
 * <p>
 * количество приносимых стрел каждый раз определял бы пользователь, вводя число arrows через консоль
 */
public class Crossbow {
    private int arrows = 10;

    // When the arrows end, the wait() method is called and releases.
    synchronized public void fire() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            for (int idx = arrows; idx >= 0; idx--) {
                if (idx != 0) {
                    System.out.println("The arrow is " + (arrows - idx + 1) + " right on the target!");
                } else {
                    System.out.println("The arrows are over");
                    arrows = 0;
                    System.out.println("Count arrows " + arrows);
                    System.out.println("Carry a new quiver with arrows!!");
                    notify();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // reload() brings new arrows, calls the notify() method, which awakens the thread
    synchronized public void reload() {
        Scanner scanner = new Scanner(System.in);
        arrows = getArrows(scanner);
        while (true) {
            notify();
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("New arrows on the way!");
            arrows = getArrows(scanner);
            System.out.println("Count arrows = " + arrows);
        }
    }

    private static int getArrows(Scanner scanner) {
        System.out.println("Enter the number of arrows: ");
        while (!scanner.hasNextInt()) {
            System.out.println("The data is incorrect.\n Enter the number of arrows again : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Crossbow crossbow = new Crossbow();
        Thread robinHood = new Thread(crossbow::fire);
        Thread servant = new Thread(crossbow::reload);
        robinHood.start();
        servant.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

