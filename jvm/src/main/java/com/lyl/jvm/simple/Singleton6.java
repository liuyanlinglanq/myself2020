package com.lyl.jvm.simple;

/**
 * 通过内部类
 *
 * @author liuyanling
 * @date 2021-10-12 18:44
 */
public class Singleton6 {

    private Singleton6() {
    }

    private static class Inner {
        //内部类内部懒汉式
        static final Singleton6 INSTANCE = new Singleton6();
    }

    //只有调用getInstance才会触发内部类实例化
    public static Singleton6 getInstance() {
        return Inner.INSTANCE;
    }

}
