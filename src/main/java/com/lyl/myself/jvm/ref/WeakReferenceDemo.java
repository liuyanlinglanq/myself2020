package com.lyl.myself.jvm.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用,只要GC了就收
 * 1. 若引用的对象还在,则也不能回收;
 * 2. 若引用的对象不在了,只要gc,就回收;
 * 3. 用弱引用,放new Object(),就不用设置object1为null了,只要gc就会回收
 *
 * @author liuyanling
 * @date 2020-12-12 17:48
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
//        Object object1 = new Object();
        WeakReference<Object> object2 = new WeakReference<>(new Object());
//        System.out.println(object1);
        System.out.println(object2.get());

        System.out.println("=================");
//        object1 = null;
        System.gc();

//        System.out.println(object1);
        System.out.println(object2.get());
    }
}
