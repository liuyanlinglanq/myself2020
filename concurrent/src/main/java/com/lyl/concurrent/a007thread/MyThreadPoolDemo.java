package com.lyl.concurrent.a007thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description MyThreadPoolDemo
 * 获取线程的方法:
 * 继承thread
 * 实现runnable
 * 实现callable
 * 通过线程池
 *
 * @author liuyanling
 * @date 2020-12-10 20:50
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
//        threadPoolInit();

        //自己的线程池,核心2,最大5,1秒过期,3个位置的队列,默认的线程工厂Executors,默认的拒绝策略;
        ExecutorService threadPool = new ThreadPoolExecutor(
                2, 5, 1L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        //最大5+队列3,>8,从9开始就会报,AbortPolicy:ava.util.concurrent.RejectedExecutionException: 拒绝策略
        //CallerRunsPolicy  main 	 办理业务,受不了了回退,回退到调用者
        //DiscardOldestPolicy,去掉最老的,只执行8个
        //DiscardPolicy, 直接丢包,只执行8个

        try {
            for (int i = 1; i <= 12; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " \t 办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }

    private static void threadPoolInit() {
        //4核
//        System.out.println(Runtime.getRuntime().availableProcessors());

//        //一池5线程
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
////        //一池一线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //一池N个线程,看情况
        ExecutorService threadPool = Executors.newCachedThreadPool();

        //模拟10个用户来办理业务,每个用户就是一个来自外部的请求线程
        //5个人来
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " \t 办理业务");
                });

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
