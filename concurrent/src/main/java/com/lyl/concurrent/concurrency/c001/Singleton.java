package com.lyl.concurrent.concurrency.c001;


/**
 * 单例模式
 */
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(Singleton::getInstance);
        Thread thread2 = new Thread(Singleton::getInstance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

}
