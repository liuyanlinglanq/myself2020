package com.lyl.myself.concurrency.c009;

public class MyRunner implements Runnable {

    @Override
    public void run() {
        // 线程需要执行的代码 ......
        System.out.println(1);
    }

    public static void main(String[] args) {
        // 创建线程对象
        Thread myRunner = new Thread(new MyRunner());
        myRunner.start();
    }
}
