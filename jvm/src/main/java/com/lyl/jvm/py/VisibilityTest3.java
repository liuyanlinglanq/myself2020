package com.lyl.jvm.py;

import java.util.concurrent.CountDownLatch;

public class VisibilityTest3 {
    static volatile boolean start = false;
    static volatile boolean end = false;

    public static void main(String[] args) throws InterruptedException {
        Thread[] runners = new Thread[10];
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < runners.length; i++) {
            runners[i] = new Thread(() -> {
                int dis = 1000 * 1000;
                while (!start) {
                    System.out.println("Runner is waiting: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
                int run = 0;
                while (!end) {
                    run += 1000;
                    System.out.println("Runner is running: " + Thread.currentThread().getName());
                    synchronized (VisibilityTest3.class) {
                        if (run >= dis&&!end) {
                            latch.countDown();
                            System.out.println("Runner win: " + Thread.currentThread().getName());
                            return;
                        }
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Runner lost: " + Thread.currentThread().getName());
            });
            runners[i].start();
        }
        Thread judger = new Thread(() -> {
            start = true;
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end = true;
        });
        judger.start();
        Thread.sleep(10000);
    }
}