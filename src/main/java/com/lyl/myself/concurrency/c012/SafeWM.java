package com.lyl.myself.concurrency.c012;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 识别共享变量间的约束条件
 * 库存管 理里面有个合理库存的概念，库存量不能太高，也不能太低，它有一个上限和一个下限。库存下限要小于库存上限
 */
public class SafeWM {

    //库存上限
    private final AtomicLong upper = new AtomicLong();
    //库存下限
    private final AtomicLong lower = new AtomicLong();

    private Object lock = new Object();

    // 设置库存上限
     void setUpper(long v) {
        synchronized (upper) {
            // 检查参数合法性
            if (v < lower.get()) {
//                System.out.println("上限比下限小");
                return;
            }
            upper.set(v);
        }

    }


    // 设置库存下限
    void setLower(long v) {
        synchronized (upper) {
            // 检查参数合法性
            if (v > upper.get()) {
//                System.out.println("下限比上限大");
                return;
            }
            lower.set(v);
        }

    }

    // 省略其他业务代码

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            SafeWM wm = new SafeWM();
            wm.setUpper(10);
            wm.setLower(2);
            Thread t1 = new Thread(() -> {
                   wm.setUpper(5);
            });

            Thread t2 = new Thread(() -> {
                wm.setLower(7);
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
            //都没有synchronized,7次会出现 ,还有 synchronized (new Object())
            //synchronized,synchronized(lock),synchronized(upper)都可以
            if (wm.lower.toString().equals("7") && wm.upper.toString().equals("5")) {
                System.out.println(wm.lower + "  " + wm.upper);
            }
        }
    }
}
