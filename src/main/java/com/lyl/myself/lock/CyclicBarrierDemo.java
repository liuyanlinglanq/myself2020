//package com.lyl.myself.lock;
//
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
///**
// * 循环的栅栏
// * 可循环利用的
// * <p>
// * 集齐7颗龙珠,召唤神龙;
// * cyclicBarrier,设计计数器,等到了该数值就会执行方法()->{}
// * <p>
// * 7个线程,每个收集一颗龙珠,并await(); 每个线程都需要await等
// * <p>
// * 7个人开会,6个人到了,每个人都要等,等到7个人齐了,才能正式开会;
// * <p>
// * 1 	 收集到龙珠
// * 2 	 收集到龙珠
// * 3 	 收集到龙珠
// * 4 	 收集到龙珠
// * 5 	 收集到龙珠
// * 6 	 收集到龙珠
// * 7 	 收集到龙珠
// * 7 	 召唤神龙
// */
//public class CyclicBarrierDemo {
//
//    public static void main(String[] args) {
//
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
//            System.out.println(Thread.currentThread().getName() + " \t 召唤神龙");
//        });
//
//        for (int i = 1; i <= 7; i++) {
//            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName() + " \t 收集到龙珠");
//                //7颗龙珠等着
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            }, String.valueOf(i)).start();
//        }
//
//
//    }
//
//}
