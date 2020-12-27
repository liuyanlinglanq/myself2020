package com.lyl.myself.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁Demo
 * <p>
 * 循环尝试获取锁,而不是wait阻塞在那;
 * 本质: while循环 + CAS思想
 * <p>
 * 原子引用线程
 * myLock: 获取当前线程 打印
 * <p>
 * while(true) {执行; false:退出 等}
 * while(若当前值是null,正好也是空,我就将当前线程放进去,取反,表示:我期待当前不是null,这样就可以等了,才能往下走;若是null,我就可以换成我当前线程,就可以停止了)
 * 我希望卫生间没人;
 * <p>
 * myUnLock: 获取打印当前线程
 * 如果当前是:当前线程,就解锁,变成null; 我希望我自己出去,卫生间是我,设置成null,给下一个人用;不需要循环解锁,就我自己;
 * <p>
 * main线程:
 * AA线程:先加锁,休息5秒,再解锁  (第一次没有,直接加锁成功,不用循环很多次)
 * 休息一秒
 * BB线程:先加锁,再解锁 (1秒之后,BB要占有,但是被人占有着,5秒,所以循环4次,之后,才能获取到
 *
 * @author liuyanling
 * @date 2020-11-28 12:44
 */
public class SpinLockDemo {


    //一个共享资源
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 自旋锁,需要两个线程
     * 1个长期占用,不放资源; 重点是加锁,不解锁;
     * 1个循环询问,
     * 所以:加锁是循环的,解锁则不需要;
     *
     * @param args
     */
    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();

        //AA加锁,好多秒;
        new Thread(() -> {
            demo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.myUnLock();
        }, "AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            demo.myLock();
            demo.myUnLock();
        }, "BB").start();


    }

    /**
     * 由于线程本身不是阻塞的,所以等等是在外面等;但是线程BB怎么证明他可以干别的呢?
     */
    private void myLock() {
        //当前线程
        Thread thread = Thread.currentThread();

        //如果当前没有线程占用资源,则我占用,我占用就停止了;否则,你就需要再来问问
        System.out.println(thread.getName() + " \t come in ");

        while (!atomicReference.compareAndSet(null, thread)) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println(thread.getName() + " \t 资源没释放,先抽根烟去 ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(thread.getName() + " \t 拿到资源了 ");

    }


    /**
     * 解锁,资源;若当前资源确实是我占有的,我放开,为null;
     */
    private void myUnLock() {
        //当前线程
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " \t myUnLock() finish ");


    }

}
