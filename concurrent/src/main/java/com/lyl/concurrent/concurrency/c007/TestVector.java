package com.lyl.concurrent.concurrency.c007;

import java.util.Vector;

/**
 * 添加10K,竞态条件
 */
public class TestVector {

    /**
     * contains(add())存在竞态条件
     * 竞态条件: 指的是程序的执 行结果依赖线程执行的顺序。
     * <p>
     * synchronized
     */
    public void addIfNotExist(Vector v, Object o) {
        synchronized (v) {
            if (!v.contains(o)) {
                v.add(o);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestVector test = new TestVector();

        Object o = new Object();

        for (int i = 0; i < 1000; i++) {
            Vector v = new Vector();
            Thread t1 = new Thread(() -> {
                test.addIfNotExist(v, o);
            });
            Thread t2 = new Thread(() -> {
                test.addIfNotExist(v, o);
            });

            Thread t3 = new Thread(() -> {
                test.addIfNotExist(v, o);
            });
            Thread t4 = new Thread(() -> {
                test.addIfNotExist(v, o);
            });
            t1.start();
            t2.start();
            t3.start();
            t4.start();

            t1.join();
            t2.join();
            t3.join();
            t4.join();

            if (v.size() > 1) {
                //执行3次,终于又一次 是2了;哈哈哈
                System.out.println(v.size());
            }
        }


    }
}
