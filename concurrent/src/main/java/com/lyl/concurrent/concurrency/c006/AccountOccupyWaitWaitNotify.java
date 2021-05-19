package com.lyl.concurrent.concurrency.c006;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 破坏占用且等待 --> 我占了一部分,还要等着另一部分的资源; -->我一次性都占够
 * 添加一个Allocator,管理员类,同时获取和释放;
 * 如果apply() 操作耗时非常短，而且并发冲突量也不大时,还是很不错的;否则,while就太消耗CPU了
 * 最好的方案应该是:
 * * * 如果线程要求的条件(转出账本和转入账本同在文件架 上)不满足，则线程阻塞自己，进入等待状态;
 * * * 当线程要求的条件(转出账本和转入账本同在文 件架上)满足后，通知等待的线程重新执行。
 * * * 其中，使用线程阻塞的方式就能避免循环等待消耗 CPU 的问题。
 */
public class AccountOccupyWaitWaitNotify {

    private Allocator allocator = Allocator.getInstance();

    private Integer balance = 200;


    public void transfer(AccountOccupyWaitWaitNotify target, Integer amt) {
        //申请资源
        allocator.apply(this, target);

        try {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                        System.out.println(this.balance + "     " + target.balance);
                    }
                }
            }
        } finally {
            allocator.free(this, target);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //两个资源 from ,to
        //每次是两个线程,t1和t2,循环10万次;那就有20万个线程;
        //每次t1和t2都是要from 和 to这两个资源,如果t1拿到了,那t2就进入等待队列,甚至其他的也进入;
        //等t1用完了,free,notifyAll,则唤醒一个,去拿from和to;
        //进入的等待队列是哪里????
        AccountOccupyWaitWaitNotify from = new AccountOccupyWaitWaitNotify();
        AccountOccupyWaitWaitNotify to = new AccountOccupyWaitWaitNotify();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Thread t1 = new Thread(() -> {
                from.transfer(to, 100);
            });

            Thread t2 = new Thread(() -> {
                to.transfer(from, 100);
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }

        //10万次2247次
        System.out.println(from.allocator.index);
        Long endTime = System.currentTimeMillis();
        //耗时:21562
        System.out.println("耗时:" + (endTime - startTime));
    }

}


class Allocator {

    private static Allocator instance;
    public static AtomicInteger index = new AtomicInteger(0);

    private Allocator() {
    }

    public static Allocator getInstance() {
        if (instance == null) {
            synchronized (Allocator.class) {
                if (instance == null) {
                    instance = new Allocator();
                }
            }
        }
        return instance;
    }

    private List<Object> lockList = new ArrayList<>();


    /**
     * 释放,从list中删除
     */
    public synchronized void free(Object from, Object to) {
        lockList.remove(from);
        lockList.remove(to);
        //满足条件,唤醒
        this.notifyAll();
    }

    /**
     * 一次性申请所有资源,成功,返回true;
     * 当前lockList中没有记录,就能一次性申请所有资源;否则,不行;
     */
    public synchronized void apply(Object from, Object to) {
        //--应该是!als.contains(from) || !als.contains(to)才wait()吧,作者回复: 有发现一个大bug，多谢多谢!
//        if (lockList.contains(from) || lockList.contains(to)) {

        // 经典写法
        while (lockList.contains(from) || lockList.contains(to)) {
            //不满足条件,阻塞
            try {
                wait();
                index.getAndIncrement();
            } catch (Exception e) {
            }
        }

        lockList.add(from);
        lockList.add(to);

    }
}
