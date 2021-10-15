
package com.lyl.jvm.simple;

/**
 * description Test
 *
 * @author liuyanling
 * @date 2021-10-12 14:34
 */
public class Test {

    public static void main(String[] args) {
//        int i = 1;
//        i = i++;
//        int j = i++;
//        int k = i + ++i * i++;
//        System.out.println("i=" + i);
//        System.out.println("j=" + j);
//        System.out.println("k=" + k);


//        Singleton1 singleton1 = Singleton1.INSTANCE;
//        Singleton1 singleton2 = Singleton1.INSTANCE;
//        System.out.println("singleton1=" + singleton1 + (singleton1 == singleton2));
//
//
//        Singleton2 singleton21 = Singleton2.INSTANCE;
//        System.out.println("singleton21=" + singleton21);


        Singleton3 singleton3 = Singleton3.INSTANCE;
        System.out.println(singleton3);
    }
}
