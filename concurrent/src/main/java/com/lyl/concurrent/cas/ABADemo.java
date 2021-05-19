package com.lyl.concurrent.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题
 *
 * @author liuyanling
 * @date 2020-11-25 23:14
 */
public class ABADemo {

    //普通的原子类
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("===============ABA 产生===============");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            //暂停一秒,保证t1先执行ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + " \t current data:" + atomicReference.get());
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============ABA 解决===============");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " \t第一次版本号" + stamp);

            //暂停一秒,保证t1先执行ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " \t第二次版本号" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " \t第三次版本号" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " \t第一次版本号" + stamp);

            //暂停3秒,保证t3完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " \t修改成功否:" + atomicStampedReference.compareAndSet(100,
                    2019, stamp,
                    stamp + 1) + "\t 当前实际最新版本号" + atomicStampedReference.getStamp() + "\t " +
                    "当前实际最新值" + atomicStampedReference.getReference());

        }, "t4").start();


        //结果:
        //===============ABA 产生===============
        //true 	 current data:2019
        //===============ABA 解决===============
        //t3 	第一次版本号1
        //t4 	第一次版本号1
        //t3 	第二次版本号2
        //t3 	第三次版本号3
        //t4 	修改成功否:false	 当前实际最新版本号3	 当前实际最新值100
    }
}
