package com.lyl.jvm.py.O002;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * description Volatile
 *
 * @author liuyanling
 * @date 2021-04-29 14:23
 */
public class Volatile {
    /**
     * 共享变量
     * 1. 不加volatile Thread-9 run after second: 0 或者 什么也不打印
     * 因为start变成true之后线程不知道,所以一直被while(true)阻塞着,不断的加wait,无法往下走.
     * 而正好线程9是在main将start改为true之后执行的,所以没有进入while(true)打印了出来,且是0;
     * 2. 加了 volatile
     * Thread-7 run after second: 52041145
     * Thread-8 run after second: 32308727
     * Thread-9 run after second: 30757341
     * Thread-3 run after second: 107588057
     * Thread-2 run after second: 108071881
     * Thread-4 run after second: 68831667
     * Thread-6 run after second: 55283432
     * Thread-5 run after second: 69552260
     * Thread-1 run after second: 109913395
     * Thread-0 run after second: 117195320
     */
    boolean start = false;


    public static void main(String[] args) throws InterruptedException {
//        new Volatile().testVolatile();

        //没有效果
//        for (int i = 0; i < 100000; i++) {
//            new Volatile().testOrder();
//        }

//        new Volatile().testABA();
//        new Volatile().testStampABA();
    }

    /**
     * 10个线程,打印自己启动之后,等start标识等了多少次;
     * main主线程会把start从false改为true;
     */
    private void testVolatile() {
        int count = 10;
        Thread[] threadArray = new Thread[count];
        for (int i = 0; i < count; i++) {
            threadArray[i] = new Thread(() -> {
                int wait = 0;
                while (!start) {
                    wait++;
                }
                System.out.println(Thread.currentThread().getName() + " run after second: " + wait);
            });

            threadArray[i].start();
        }
        start = true;

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 指令重排
     * 写一个类,writer和reader,write两个值,看是不是存在按顺序的下面的写好了,但是上面没写好的情况(测不出来)
     */
    private void testOrder() {
        //两个线程一个写,一个读
        ReOrderExample example = new ReOrderExample();
        Thread writer = new Thread(example::writer);

        Thread reader = new Thread(example::reader);

        writer.start();
        reader.start();

    }

    private static void testABA() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(1);

        new Thread(() -> {
            int x = num.get();
            int n = num.get() + 1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = num.compareAndSet(x, n);
            System.out.println(b);
        }).start();

        Thread.sleep(5);
        num.compareAndSet(num.get(), num.get() + 1);
        System.out.println("num 1:" + num.get());

        num.compareAndSet(num.get(), num.get() - 1);
        System.out.println("num 2: " + num.get());
    }

    private void testStampABA() throws InterruptedException {
        AtomicStampedReference<Integer> num = new AtomicStampedReference<>(0, 1);

        new Thread(() -> {
            int stamp = num.getStamp();
            int x = num.getReference();
            int n = num.getReference() + 1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = num.compareAndSet(x, n, stamp, stamp + 1);
            System.out.println(b);
        }).start();

        Thread.sleep(5);
        num.compareAndSet(num.getReference(), num.getReference() + 1, num.getStamp(), num.getStamp() + 1);
        System.out.println("num 1:" + num.getReference() + " , stamp1: " + num.getStamp());

        num.compareAndSet(num.getReference(), num.getReference() - 1, num.getStamp(), num.getStamp() + 1);
        System.out.println("num 2:" + num.getReference() + " , stamp2: " + num.getStamp());
    }

}


class ReOrderExample {
    private int a = 0;
    private boolean flag = false;

    void writer() {
        a = 1;
        flag = true;
    }

    void reader() {
        //若flag 已经是1了,且a不是0,
        // 打印(若按顺序而言,应该是a先变成1,所以flag=true的时候a一定是1)但是因为指令重排,所以不一定.
        if (flag) {
            int i = a * a;
            if (i != 1) {
                System.out.println(i);
            }
        }
    }

}
