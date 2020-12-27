//package com.lyl.myself.lock;
//
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//class Phone implements Runnable {
//
//    public synchronized void sendSMS() throws Exception {
//        System.out.println(Thread.currentThread().getId() + " \t invoked sendSMS()");
//
//        sendEmail();
//    }
//
//    public synchronized void sendEmail() throws Exception {
//        System.out.println(Thread.currentThread().getId() + " \t invoked sendEmail()");
//    }
//
//    //===================================================
//    Lock lock = new ReentrantLock();
//
//    @Override
//    public void run() {
//        get();
//    }
//
//    private void get() {
//        //加锁几次,解锁就要几次;否则会卡死
//        lock.lock();
//        lock.lock();
//        try {
//            //线程可以进入任何一个它已经拥有的锁
//            //
//            //
//            // 所同步着的代码块
//            System.out.println(Thread.currentThread().getName()+" \t invoked get()" );
//            set();
//        } finally {
//            lock.unlock();
//            lock.unlock();
//        }
//    }
//
//    private void set() {
//        lock.lock();
//        try {
//            System.out.println(Thread.currentThread().getName()+" \t ####invoked set()" );
//        } finally {
//            lock.unlock();
//        }
//    }
//}
//
///**
// * 可重入锁
// * <p>
// * 指的是同一个线程外层函数获得锁之后,内层递归函数仍然能获取该锁的代码;
// * 在同一个线程在外层方法获取锁的时候,在进入内层方法会自动获取锁
// * <p>
// * 也即是说,线程可以进入任何一个它已经拥有的锁所同步着的代码块
// * case one synchronized是一个典型的可重入锁
// * 10 	 invoked sendSMS()   t1线程外层函数获得锁之后
// * 10 	 invoked sendEmail() t1线程在进入内层方法会自动获取锁
// * 11 	 invoked sendSMS()
// * 11 	 invoked sendEmail()
// * case two ReentrantLock是一个典型的可重入锁
// *
// * @author liuyanling
// * @date 2020-11-27 00:05
// */
//public class ReenterLockDemo {
//
//    public static void main(String[] args) {
//        Phone phone = new Phone();
//        new Thread(() -> {
//            try {
//                phone.sendSMS();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, "t1").start();
//
//        new Thread(() -> {
//            try {
//                phone.sendSMS();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();
//
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//
//        Thread t3 = new Thread(phone,"t3");
//        Thread t4 = new Thread(phone,"t3");
//
//        t3.start();
//        t4.start();
//    }
//}
