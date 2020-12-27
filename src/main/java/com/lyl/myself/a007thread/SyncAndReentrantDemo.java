package com.lyl.myself.a007thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁,可通过锁绑定多个条件condition,做到分组唤醒,精确唤醒
 * <p>
 * 题目:多线程之间顺序调用,保证线程A->B->C按顺序启动,其中A线程打印5次,B线程打印10次,C线程打印15次;共来10轮
 *
 * @author liuyanling
 * @date 2020-12-09 20:17
 */
public class SyncAndReentrantDemo {

    public static void main(String[] args) {

        int count = 2;
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                shareResource.print15();
            }
        }, "C").start();


        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                shareResource.print10();
            }
        }, "B").start();


        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                shareResource.print5();
            }
        }, "A").start();


    }
}

class ShareResource {
    //使用number 作为标志位;A:1 B:2, C:3
    private int number = 1;
    private Lock lock = new ReentrantLock();
    //3个线程3个条件
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            //不是1就等着
            while (number != 1) {
                c1.await();
            }
            //是1就打印5次
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i);
            }

            //唤醒下一个
            number = 2;
            //精确唤醒 c2
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print10() {
        lock.lock();
        try {
            //不是2就等着
            while (number != 2) {
                c2.await();
            }
            //是2就打印10次
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i);
            }

            //唤醒下一个
            number = 3;
            //精确唤醒 c3
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15() {
        lock.lock();
        try {
            //不是3就等着
            while (number != 3) {
                c3.await();
            }
            //是2就打印15次
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i);
            }

            //唤醒下一个,循环也就是第一个
            number = 1;
            //精确唤醒 c1
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
