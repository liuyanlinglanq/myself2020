package com.lyl.myself.concurrency.c001;

import java.util.concurrent.atomic.AtomicLong;

public class TestSynchronizedAdd10K {

    private static long count = 0;

    public synchronized void add10K() {
        int idx = 0;
        //100000000 102082140
        while (idx++ < 100000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final TestSynchronizedAdd10K testAdd10K = new TestSynchronizedAdd10K();
        Thread thread1 = new Thread(testAdd10K::add10K);
        Thread thread2 = new Thread(testAdd10K::add10K);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(calc());
    }
}
