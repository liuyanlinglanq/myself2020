package com.lyl.myself.volatiledemo;

/**
 * 单例模式
 * <p>
 * 单机版下的单例模式
 * if (instance == null) {
 * instance = new SingletonDemo();
 * }
 * return instance;
 *
 * @author liuyanling
 * @date 2020-11-22 17:58
 */
public class SingletonDemo {

    //加上volatile 静止指令重排
    private static volatile SingletonDemo instance;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + " \t 我是构造函数");
    }

    /**
     * 加 synchronized 太重
     * DCL double check lock双重检测机制
     *
     * @return
     */
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //结果:
        //1 	 我是构造函数
        //3 	 我是构造函数
        //2 	 我是构造函数

        //DCL : 1 	 我是构造函数
        //多线程
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
