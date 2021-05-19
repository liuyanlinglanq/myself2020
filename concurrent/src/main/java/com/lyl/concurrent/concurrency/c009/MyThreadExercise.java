package com.lyl.concurrent.concurrency.c009;

public class MyThreadExercise extends Thread {


    @Override
    public void run() {
        Thread th = Thread.currentThread();
        while (true) {
            if (th.isInterrupted()) {
                break;
            }

            System.out.println(false);
            // 省略业务代码无数
            try {
                //线程进入TIMED_WAITING状态
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建线程对象
        MyThreadExercise my = new MyThreadExercise();
        my.start();

        Thread otherMy = new Thread(() -> {
            my.interrupt();
        });

//        Thread.sleep(500);
        otherMy.start();


    }

}
