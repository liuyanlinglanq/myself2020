package com.lyl.concurrent.volatiledemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 可见性 示例
 * 1. 验证volatile的可见性
 * 1.1 假如int num = 0; num变量之前没有加volatile关键字修饰
 *
 * @author liuyanling
 * @date 2020-11-22 15:07
 */
public class VolatileDemo {

    public static void main(String[] args) {
//        seeOkByVolatile();

        MyData myData = new MyData();

        //20个线程,每个线程加1000次,若保证原子性,在应该为2万;
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomicInteger();
                }
            }, String.valueOf(i)).start();
        }

        //等待上面20个线程,都执行完,再用main获取结果值 ,>2 (2:GC线程,main线程) + 一个其他线程
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        //结果:main	 finally num value: 19341
        //加了synchronized,结果:main	 finally num value: 20000


        //结果:
        //main	 finally num value: 19956
        //main	 finally atomicInteger value: 20000
        System.out.println(Thread.currentThread().getName() + "\t finally num value: " + myData.num);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicInteger value: " + myData.atomicInteger);
    }

    /**
     * volatile 可见性 示例
     * * 1. 验证volatile的可见性
     * * 1.1 假如int num = 0; num变量之前没有加volatile关键字修饰,没有可见性
     * * 1.2 添加了volatile,可以解决可见性
     * <p>
     * 2.验证volatile不保证原子性
     * 2.1 原子性是? 不可分割,完整性,也即某个线程在做某个具体业务时,中间不可以被加塞,或者被分割,需要整体完整;要么同时成功,要么同时失败;
     * 2.2 不保证原子性 案例演示
     * 2.3 why? 字节码是三个操作
     * 2.4 如何解决
     *  2.4.1 sync 重量了
     *  2.4.2 原子性,使用juc下的AtomicInteger;
     */
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        //第一个线程AAA,操作myData资源类
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in ");
            //暂停一下
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addToSixty();
            System.out.println(Thread.currentThread().getName() + "\t update num value: " + myData.num);
        }, "AAA").start();

        //第二个线程,main,3秒之后,才会变为60;所以此时应该是0
        while (myData.num == 0) {
            //main线程就一直在这里等待,直到num不为0;
            //volatile 变量修改了,通知main,才会跳出该循环
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is ove,num value" + myData.num);
    }
}

/**
 * 共享变量
 * MyData.java --> MyData.class -> JVM字节码
 */
class MyData {

    volatile int num = 0;

    void addToSixty() {
        this.num = 60;
    }

    // 此时,是加了volatile修饰的;
    void addPlusPlus() {
        this.num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    void addAtomicInteger() {
        atomicInteger.incrementAndGet();
    }
}
