package Entities;

import java.util.Random;

public class Consumer implements Runnable {
    private final Factory factory;

    public Consumer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        while (this.factory.isRunning) {
            Random rd = new Random();
            int resourceAmount = rd.nextInt(8) + 1;
            this.factory.useResources(resourceAmount);

            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
