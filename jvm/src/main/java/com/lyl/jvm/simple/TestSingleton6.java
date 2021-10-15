package com.lyl.jvm.simple;

/**
 * description TestSingleton6
 *
 * @author liuyanling
 * @date 2021-10-12 18:52
 */
public class TestSingleton6 {

    public static void main(String[] args) {
        Singleton6 singleton1 = Singleton6.getInstance();
        Singleton6 singleton2 = Singleton6.getInstance();
        System.out.println((singleton1 == singleton2) + " " + singleton1 + " " + singleton2);
    }

}
