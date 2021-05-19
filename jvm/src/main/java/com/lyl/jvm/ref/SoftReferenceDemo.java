package com.lyl.jvm.ref;

import java.lang.ref.SoftReference;

/**
 * 软引用 内存够不回收;内存不够回收
 *
 * @author liuyanling
 * @date 2020-12-12 17:29
 */
public class SoftReferenceDemo {

    public static void main(String[] args) {
//        memoryEnough();
        memoryNotEnough();
    }

    /**
     * 内存足够
     * java.lang.Object@31befd9f
     * java.lang.ref.SoftReference@1c20c684
     * null
     * java.lang.ref.SoftReference@1c20c684
     */
    public static void memoryEnough() {
        Object object1 = new Object();
        SoftReference<Object> object2 = new SoftReference<>(object1);
        System.out.println(object1);
        System.out.println(object2);

        object1 = null;
        //内存足,是不会有用的
        System.gc();

        System.out.println(object1);
        System.out.println(object2);
    }

    /**
     * 内存不足
     * -Xms5M -Xmx5M  -XX:+PrintGCDetails 配起来,不需要自己 System.gc();
     * <p>
     * java.lang.Object@31befd9f
     * java.lang.Object@31befd9f
     * java.lang.OutOfMemoryError: Java heap space
     * at SoftReferenceDemo.memoryNotEnough(SoftReferenceDemo.java:53)
     * at SoftReferenceDemo.main(SoftReferenceDemo.java:15)
     * null
     * null
     *
     * 问:只有两个对象的时候,软引用确实变为null了,为什么加了一个Object3之后就有值了? Object3=object1;
     * 若 Object object3 = new Object();则object2还是会变成null;
     */
    public static void memoryNotEnough() {
        Object object1 = new Object();
        SoftReference<Object> object2 = new SoftReference<>(new Object());
        Object object3 = new Object();
        System.out.println(object1);
        System.out.println(object3);
        System.out.println(object2.get());

        object1 = null;

        try {
            Byte[] bytes = new Byte[10 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(object1);
            System.out.println(object2.get());
            System.out.println(object3);
        }

    }
}
