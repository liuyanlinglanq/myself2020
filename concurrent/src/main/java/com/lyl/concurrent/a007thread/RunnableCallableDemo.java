package com.lyl.concurrent.a007thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * description RunnableCallableDemo
 *
 * @author liuyanling
 * @date 2020-12-10 19:55
 */
public class RunnableCallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //main,和futureTask两个线程
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        //同一个FutureTask只会调用一个,另一个复用上一个的结果
        new Thread(futureTask, "AA").start();
        new Thread(futureTask, "BB").start();

        //两个FutureTask才会执行2次
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        new Thread(futureTask1, "CC").start();

        System.out.println(Thread.currentThread().getName() + " \t main");
        int result = 100;

        //没算完等着
        while (!futureTask.isDone()) {

        }
        //放在最后,不要阻塞其他线程
        int result2 = futureTask.get();
        System.out.println(result + result2);
    }
}

class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " \t callable");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}
