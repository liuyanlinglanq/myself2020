package com.lyl.concurrent.dead;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

//1个线程类,有两个资源,用构造函数传入,
// 在run方法中,用synchronized锁其中一个,然后在里面锁另一个;
// main方法中,启动两个线程,将两个资源调换传入;
class HoldData implements Runnable {
    private String lockA;
    private String lockB;

    public HoldData(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @SneakyThrows
    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " \t 获取到" + lockA + "  等待:" + lockB);
            TimeUnit.SECONDS.sleep(2);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " \t 获取到" + lockB);
            }
        }
    }
}

/**
 * description DeadLockDemo
 * 两个/两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象；
 * 若无外力干涉，那他们将无法推进下去，如果系统资源充足，进程的资源请求都能够得到满足，死锁出现的可能性就很低，
 * 否则就会因争夺有限的资源而陷入死锁。
 *
 * @author liuyanling
 * @date 2020-12-11 21:09
 */
public class DeadLockDemo {

    public static void main(String[] args) {

        /**
         *  linux ps -ef | grep xxx   ls -l
         *  window下的java运行程序,也有类似的ps的查看进程的命令,但是目前我们需要查看的只是java
         *  jps = java ps     jps -l
         *  jstack 进程号 分析是否是死锁;
         */

        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldData(lockA, lockB), "AA").start();
        new Thread(new HoldData(lockB, lockA), "BB").start();
    }
}
