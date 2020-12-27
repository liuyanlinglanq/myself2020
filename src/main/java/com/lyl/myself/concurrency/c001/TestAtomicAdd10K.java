package com.lyl.myself.concurrency.c001;

import java.util.concurrent.atomic.AtomicLong;

public class TestAtomicAdd10K {

    private static AtomicLong count = new AtomicLong(0);

    public void add10K() {
        int idx = 0;
        //100000000 102082140
        while (idx++ < 100000) {
            count.getAndIncrement();
        }
    }

    public static long calc() throws InterruptedException {
        final TestAtomicAdd10K testAdd10K = new TestAtomicAdd10K();
        Thread thread1 = new Thread(testAdd10K::add10K);
        Thread thread2 = new Thread(testAdd10K::add10K);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(calc());
    }
}
