package com.lyl.concurrent.concurrency.c012;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 识别共享变量间的约束条件
 * 库存管 理里面有个合理库存的概念，库存量不能太高，也不能太低，它有一个上限和一个下限。库存下限要小于库存上限
 */
public class SafeWMCompareAndSet {

    //库存上限
    private final AtomicLong upper = new AtomicLong();
    //库存下限
    private final AtomicLong lower = new AtomicLong();

    // 设置库存上限
    void setUpper(long v) {
        // 检查参数合法性
        upper.compareAndSet(upper.longValue(), v);
    }


    // 设置库存下限
    void setLower(long v) {
        lower.compareAndSet(lower.longValue(), v);
    }

    // 省略其他业务代码

    public static void main(String[] args) throws InterruptedException {


        for (int i = 0; i < 1000; i++) {
            SafeWMCompareAndSet wm = new SafeWMCompareAndSet();
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
            System.out.println(wm.lower + "  " + wm.upper);
        }
    }
}
