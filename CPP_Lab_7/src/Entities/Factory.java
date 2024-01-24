package Entities;

import javax.swing.*;

public class Factory {
    private int totalResources;
    private final Object lock;
    public boolean isRunning;

    private final JTextArea log;

    public Factory(int totalResources, JTextArea log) {
        this.lock = new Object();
        this.totalResources = totalResources;
        this.log = log;
        isRunning = true;
    }

    public void produceGoods(int resourceAmount) {
        synchronized (lock) {
            this.totalResources += resourceAmount;
            log.append(Thread.currentThread().getName() + " produced " + resourceAmount + " resources.\n");
        }
    }

    public void useResources(int resourceAmount) {
        if (resourceAmount > this.totalResources) {
            log.append(Thread.currentThread().getName() + " cannot use " + resourceAmount + " resources, since it is more than total resources.\n");
            return;
        }

        synchronized (lock) {
            this.totalResources -= resourceAmount;
            log.append(Thread.currentThread().getName() + " used " + resourceAmount + " resources\n");
        }
    }

    public int getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(int totalResources) {
        this.totalResources = totalResources;
    }


}
