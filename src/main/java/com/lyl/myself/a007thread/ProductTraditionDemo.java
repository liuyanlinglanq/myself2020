package com.lyl.myself.a007thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版的生产者
 *
 * 1. 一个资源类,有生产和消费的方法,生成和消费的方法自己需要有一个标志位,
 * 标记是否可以执行,当不可以执行时,需要用await等待;当达到条件后,需要唤醒线程signalAll
 *
 * @author liuyanling
 * @date 2020-12-09 20:17
 */
public class ProductTraditionDemo {

    /**
     * 两个线程,各5轮,一个加,一个减
     *
     * @param args
     */
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        // 1 --> +1 +1 +1
        // 2 --> -1 -1 -1
        // 3 --> +1 +1 +1
        // 4 --> -1 -1 -1
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.increment();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.decrement();
            }
        }, "BB").start();


        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.increment();
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.decrement();
            }
        }, "DD").start();


//        for (int i = 1; i <= 5; i++) {
//            new Thread(() -> {
//                shareData.increment();
//            }, String.valueOf(i)).start();
//        }
//
//        for (int i = 1; i <= 5; i++) {
//            new Thread(() -> {
//                shareData.decrement();
//            }, String.valueOf(i)).start();
//        }

    }
}


/**
 * 资源类,如空调,上调一度;下调一度,是空调本身可以的操作
 * 自带 上调一度;下调一度 的方法,且资源用锁锁住了; 上调时必须是0才能+1,否则等待;下降时必须不是0 才能-1,否则等待;
 */
class ShareData {

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //上升一度(生产者)
    public void increment() {
        lock.lock();
        try {
            //while是要休息的,所以是阻塞的条件,所以是走下去的条件取反
            while (number != 0) {
                System.out.println(Thread.currentThread().getName() + "incr await");
                condition.await();
            }

            number++;
            System.out.println(Thread.currentThread().getName() + " \t空调上升一度 " + number);
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "incr signalAll");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    //下降一度 (消费者)
    public void decrement() {
        lock.lock();
        try {
            //while是要休息的,所以是阻塞的条件,所以是走下去的条件取反
            while (number == 0) {
                System.out.println(Thread.currentThread().getName() + "decr await");
                condition.await();
            }

            number--;
            System.out.println(Thread.currentThread().getName() + " \t空调下降一度 " + number);
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "decr signalAll");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


