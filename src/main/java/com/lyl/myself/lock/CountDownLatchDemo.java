package com.lyl.myself.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 倒计时(闭锁)demo
 * 一次性的
 * <p>
 * closeDoor
 * 班长关门:得等到所有(6个)同学都走了,才能关;
 * <p>
 * 如果不用闭锁,起6个线程,说他们上完自习,离开教室;
 * 然后最后班长关门;
 * 会发现,会出现班长关门了,还有人上完自习,离开教室的情况; (现实的情况下,肯定走不掉了)
 * <p>
 * 用countDownLatch,传一个数字,也就是计数器; 然后6个线程中,走一个countDown一下,减少一个;
 * 班长:await等一下,等到countDown 为0;才会执行;
 * <p>
 * <p>
 * 6国被灭,秦国统一; 不想要线程名是1-6,而是对应的国家名称; 灭韩、赵、魏、楚、燕、齐六国;
 * 使用枚举;1-6:韩、赵、魏、楚、燕、齐 CountryEnum,使用枚举,获取1-6分别的名称,相当于一个数据库的小表;
 * 此处为枚举类型的使用说明,想用就用,不想我也会了
 * <p>
 * 1 	 上完自习,离开教室
 * 4 	 上完自习,离开教室
 * 5 	 上完自习,离开教室
 * 3 	 上完自习,离开教室
 * 2 	 上完自习,离开教室
 * 6 	 上完自习,离开教室
 * main 	 关门
 * <p>
 * 齐 	 国被灭
 * 燕 	 国被灭
 * 楚 	 国被灭
 * 魏 	 国被灭
 * 赵 	 国被灭
 * 韩 	 国被灭
 * main 	 秦统一天下
 */
public class CountDownLatchDemo {

    private final static Integer count = 6;

    public static void main(String[] args) {
        // closeDoor();

        // 六国被灭
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " \t 国被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEacheGetEnum(i).getRetMessage()).start();
        }

        //主线程,等一等
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " \t 秦统一天下");


    }

    private static void closeDoor() {
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " \t 上完自习,离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        //主线程,等一等
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " \t 关门");
    }


}
