package com.lyl.myself.concurrency.c003;

public class SafeStaticCalc {
    static long value = 0L;

    //两个不同的锁,保护一个资源,两个临界区没有互斥关系,所以没有可见性保证

    /**
     * 对get的可见性,无法保证,因为get没有加锁;
     * synchronized(this)
     *
     * @return
     */
    synchronized static long get() {
        return value;
    }

    /**
     * synchronized --> 加锁,保证原子性
     * 管程中锁的规则,保证可见性 --> 解(某个)锁要Happens-Before于加(这个)锁
     * synchronized(class)
     */
    synchronized static void addOne() {
        for (int i = 0; i < 1000000; i++) {
            value += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //1539
        //1000000
        //将addOne去掉static,并且 SafeStaticCalc.addOne(); 改为 calc.addOne(); 可以保证都是10万 (都锁this)
        //将get 加上static ,也可以保证;

        SafeStaticCalc calc = new SafeStaticCalc();
        Thread thread1 = new Thread(() -> {
            SafeStaticCalc.addOne();
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(calc.get());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(value);
    }

}
