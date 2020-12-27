package com.lyl.myself.concurrency.c003;

public class TestSynchronized {

    //synchronized 修饰的方法或代码块前后自动加上lock()和unlock()
    //修饰非静态方法
    synchronized void foo() {

    }

    //修饰静态方法
    synchronized static void bar() {
        //临界区
    }

    //修饰代码块
    Object object = new Object();

    void baz() {
        synchronized (object) {
            //临界区
        }
    }
}
