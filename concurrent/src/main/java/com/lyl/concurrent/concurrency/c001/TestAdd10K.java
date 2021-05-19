package com.lyl.concurrent.concurrency.c001;

public class TestAdd10K {

    private static long count = 0;

    public void add10K() {
        int idx = 0;
        //100000000 102082140
        while (idx++ < 100000) {
            count += 1;
//            System.out.println(count);
        }
    }

    public static long calc() throws InterruptedException {
        final TestAdd10K testAdd10K = new TestAdd10K();
        Thread thread1 = new Thread(testAdd10K::add10K);
        Thread thread2 = new Thread(testAdd10K::add10K);

        thread1.start();
        thread2.start();

//        thread1.join();
//        thread2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(calc());
    }
}
