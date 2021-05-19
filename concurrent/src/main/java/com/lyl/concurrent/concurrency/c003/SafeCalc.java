package com.lyl.concurrent.concurrency.c003;

/**
 * 安全的计算
 */
public class SafeCalc {
    long value = 0L;

    /**
     * 对get的可见性,无法保证,因为get没有加锁;
     * 加上synchronized,就都锁定this,一个锁(this),锁两个资源:get()和addOne()的value资源
     *
     * @return
     */
    synchronized long get() {
        return value;
    }

    /**
     * synchronized --> 加锁,保证原子性
     * 管程中锁的规则,保证可见性 --> 解(某个)锁要Happens-Before于加(这个)锁
     */
    synchronized void addOne() {
        value += 1;
    }

    public static void main(String[] args) throws InterruptedException {
        SafeCalc calc = new SafeCalc();
        Thread thread1 = new Thread(() -> {
            calc.addOne();
        });
        //锁相同的对象,可以保证可见性
        Thread thread2 = new Thread(() -> {
            calc.addOne();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(calc.get());
    }
}
