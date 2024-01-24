package Entities;

import java.util.Random;

public class Producer implements Runnable {
    private final Factory factory;

    public Producer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        while(this.factory.isRunning) {
            Random rd = new Random();
            int resourceAmount = rd.nextInt(10) + 1;
            this.factory.produceGoods(resourceAmount);

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
