//package com.lyl.myself.lock;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.TimeUnit;
//
///**
// * 阻塞队列
// */
//public class SynchronousQueueDemo {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        //同步队列
//        BlockingQueue<String> synchronousQueue = new SynchronousQueue<String>();
//
//        new Thread(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName() + " \t put 第一个数");
//                synchronousQueue.put("a");
//
//                System.out.println(Thread.currentThread().getName() + " \t put 第2个数");
//                synchronousQueue.put("b");
//                System.out.println(Thread.currentThread().getName() + " \t put 第3个数");
//                synchronousQueue.put("c");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }, "PUT").start();
//
//
//        new Thread(() -> {
//
//
//        }, "TAKE").start();
//
//
//        new Thread(() -> {
//
//            try {
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + " \t take 第一个数" + synchronousQueue.take());
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + " \t take 第2个数" + synchronousQueue.take());
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + " \t take 第3个数" + synchronousQueue.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }, "TAKE").start();
//
//
//    }
//
//
//}
