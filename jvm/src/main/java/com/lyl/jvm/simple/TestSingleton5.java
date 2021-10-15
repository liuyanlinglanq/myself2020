package com.lyl.jvm.simple;

import java.util.concurrent.*;

/**
 * description TestSingleton5
 *
 * @author liuyanling
 * @date 2021-10-12 16:17
 */
public class TestSingleton5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton5> cc = new Callable<Singleton5>() {
            @Override
            public Singleton5 call() throws Exception {
                return Singleton5.getInstance();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<Singleton5> f1 = pool.submit(cc);
        Future<Singleton5> f2 = pool.submit(cc);

        Singleton5 singleton1 = f1.get();
        Singleton5 singleton2 = f2.get();

        System.out.println((singleton1 == singleton2) + " " + singleton1 + " " + singleton2);
        pool.shutdown();
    }
}
