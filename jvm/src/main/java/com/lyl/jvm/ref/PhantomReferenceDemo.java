package com.lyl.jvm.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 虚引用 必须配合引用队列使用,本身没有存在价值,get都是null
 *
 * @author liuyanling
 * @date 2020-12-13 13:37
 */
public class PhantomReferenceDemo {

    public static void main(String[] args) {
        weakReferenceQueue();
        System.out.println("================");
        phantomReferenceQueue();
    }

    private static void weakReferenceQueue() {
        System.out.println("=======weakReferenceQueue=========");
        Object object1 = new Object();
        //引用队列
        ReferenceQueue<Object> queue1 = new ReferenceQueue<>();
        //弱引用关联上引用队列
        WeakReference<Object> object2 = new WeakReference<>(object1, queue1);

        System.out.println(object1);
        System.out.println(object2.get());
        System.out.println(queue1.poll());
        System.out.println("================== GC ======");

        object1 = null;
        System.gc();

        System.out.println(object1);
        System.out.println(object2.get());
        System.out.println(queue1.poll());
    }

    private static void phantomReferenceQueue() {
        System.out.println("=======phantomReferenceQueue=========");
        Object object1 = new Object();
        //引用队列
        ReferenceQueue<Object> queue1 = new ReferenceQueue<>();
        //虚引用关联上引用队列
        PhantomReference<Object> object2 = new PhantomReference<>(object1, queue1);

        System.out.println(object1);
        //一直都是null
        System.out.println(object2.get());
        System.out.println(queue1.poll());
        System.out.println("================== GC ======");

        object1 = null;
        System.gc();

        System.out.println(object1);
        System.out.println(object2.get());
        //gc之后,不论是虚/弱引用都会放入到队列中
        System.out.println(queue1.poll());


    }
}
