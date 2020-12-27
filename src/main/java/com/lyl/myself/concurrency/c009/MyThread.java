package com.lyl.myself.concurrency.c009;

public class MyThread extends Thread {

    @Override
    public void run() {
        // 线程需要执行的代码 ......
        System.out.println(1);
    }

    public static void main(String[] args) {
        // 创建线程对象
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
