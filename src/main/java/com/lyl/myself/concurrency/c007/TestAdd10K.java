package com.lyl.myself.concurrency.c007;

/**
 * 添加10K,竞态条件
 */
public class TestAdd10K {

    private static long count = 0;

    public synchronized void set(Long count) {
        this.count = count;
    }

    public synchronized long get() {
        return count;
    }

    /**
     * set(get())存在竞态条件,结果165959,而不是20万;
     * 竞态条件: 指的是程序的执 行结果依赖线程执行的顺序。
     */
    public synchronized void add10K() {
        int idx = 0;
        while (idx++ < 100000) {
            //隐式依赖 get() 的 结果
            set(get() + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestAdd10K test = new TestAdd10K();
        Thread t1 = new Thread(() -> {
            test.add10K();
        });
        Thread t2 = new Thread(() -> {
            test.add10K();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(test.get());
    }
}
