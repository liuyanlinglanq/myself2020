package com.lyl.myself.concurrency.c003;

/**
 * 锁不同的对象结果无法保证
 */
public class SafeCalcNewObject {
    long value = 0L;

    /**
     * 对get的可见性,无法保证,因为get没有加锁;
     * 加上synchronized,就都锁定this,一个锁(this),锁两个资源:get()和addOne()的value资源
     *
     * @return
     */
    long get() {
        synchronized (new Object()) {
            return value;
        }
    }

    /**
     * synchronized --> 加锁,保证原子性
     * 管程中锁的规则,保证可见性 --> 解(某个)锁要Happens-Before于加(这个)锁
     * <p>
     * new Object() 和 new Object() 不是同一个对象
     */
    void addOne() {
        synchronized (new Object()) {
            for (int i = 0; i < 1000000; i++) {
                value += 1;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        //3002
        //1000000
        SafeCalcNewObject calc = new SafeCalcNewObject();
        Thread thread1 = new Thread(() -> {
            calc.addOne();
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(calc.get());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(calc.get());
    }
}
