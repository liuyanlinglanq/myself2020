package com.lyl.myself.jvm.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 虚hashMap的demo
 *
 * @author liuyanling
 * @date 2020-12-13 12:27
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        hashMapDemo();
        System.out.println("=================");
        hashMapDemo1();
        System.out.println("=================");
        weakHashMapDemo();
    }

    /**
     * 正常的new HashMap是强引用,只要活着,gc也不会回收
     */
    private static void hashMapDemo() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        hashMap.put(key, value);

        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);
    }

    /**
     * 1. hashMap的key值可以是null;2.多个null作为key,保留最后一个;
     */
    private static void hashMapDemo1() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(new Integer(3), "hashMap3");
        System.out.println(hashMap);

        hashMap.put(null,"hashMap3");
        hashMap.put(null,"hashMap4");
        System.out.println(hashMap);
    }

    /**
     * weakHashMap,若key的值无效了,则value的值也会无效;
     */
    private static void weakHashMapDemo() {
        WeakHashMap<Integer, String> hashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";
        hashMap.put(key, value);

        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);
    }


}
