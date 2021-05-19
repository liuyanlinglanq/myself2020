package com.lyl.concurrent.concurrency.c007;

import java.util.Vector;

/**
 * 安全的vecotr
 */
public class SafeVector {

    private Vector v = new Vector();

    /**
     * contains(add())存在竞态条件
     * 竞态条件: 指的是程序的执 行结果依赖线程执行的顺序。
     * <p>
     * synchronized
     */
    synchronized void addIfNotExist(Object o) {
        if (!v.contains(o)) {
            v.add(o);
        }
    }

    public Vector getVector() {
        return v;
    }

    public static void main(String[] args) throws InterruptedException {


        Object o = new Object();

        for (int i = 0; i < 1000; i++) {
            SafeVector test = new SafeVector();
            Thread t1 = new Thread(() -> {
                test.addIfNotExist(o);
            });
            Thread t2 = new Thread(() -> {
                test.addIfNotExist(o);
            });

            Thread t3 = new Thread(() -> {
                test.addIfNotExist(o);
            });
            Thread t4 = new Thread(() -> {
                test.addIfNotExist(o);
            });
            t1.start();
            t2.start();
            t3.start();
            t4.start();

            t1.join();
            t2.join();
            t3.join();
            t4.join();

            if (test.getVector().size() > 1) {
                //不加synchronized,很容易出现2;
                System.out.println(test.getVector().size());
            }
        }

    }
}
