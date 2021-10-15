package com.lyl.jvm.simple;

/**
 * description Singleton5
 *
 * @author liuyanling
 * @date 2021-10-12 16:13
 */
public class Singleton5 {
    private static volatile Singleton5 instance;

    private Singleton5() {
    }

    public static Singleton5 getInstance() {

        if (instance == null) {
            synchronized (Singleton5.class) {
                if (instance == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new Singleton5();
                }
            }
        }

        return instance;
    }
}
