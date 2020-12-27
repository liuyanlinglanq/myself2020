package com.lyl.myself.jvm.ref;

import java.util.concurrent.TimeUnit;

/**
 * 强引用  只要活着就不回收
 *
 * @author liuyanling
 * @date 2020-12-12 17:16
 */
public class StrongRefDemo {

    public static void main(String[] args) throws InterruptedException {
        //null
        //java.lang.Object@31befd9f
        //这样定义的默认就是强引用
        Object object1 = new Object();
        //object2引用复制
        Object object2 = object1;
        //置空
        object1 = null;
        System.gc();
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(object1);
        System.out.println(object2);
    }
}
