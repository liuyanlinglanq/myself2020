package com.lyl.jvm.simple;

import java.util.concurrent.*;

/**
 * description TestSingleton4
 *
 * @author liuyanling
 * @date 2021-10-12 15:41
 */
public class TestSingleton4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //单线程正确
//        Singleton4 singleton1 = Singleton4.getInstance();
//        Singleton4 singleton2 = Singleton4.getInstance();
//
//        System.out.println((singleton1 == singleton2) + " " + singleton1 + " " + singleton2);

        //多线程 会出问题,为了问题更明确,可以休息100ms
        Callable<Singleton4> cc = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Future<Singleton4> f1 = threadPool.submit(cc);
        Future<Singleton4> f2 = threadPool.submit(cc);

        Singleton4 singleton1 = f1.get();
        Singleton4 singleton2 = f2.get();

        System.out.println((singleton1 == singleton2) + " " + singleton1 + " " + singleton2);

        threadPool.shutdown();

    }
}
