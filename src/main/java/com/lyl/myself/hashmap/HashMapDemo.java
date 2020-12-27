package com.lyl.myself.hashmap;

import java.util.HashMap;

/**
 * description HashMapDemo
 *
 * @author liuyanling
 * @date 2020-12-17 17:19
 */
public class HashMapDemo {

    public static void main(String[] args) {

        //Error:java: 无效的标记: -parameters
        HashMap<Integer, String> map = new HashMap(4);
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "B");
        map.put(4, "B");

//        new Thread(() -> {
//            map.put(3, "C");
//        }, "AA").start();
//
//        new Thread(() -> {
//            map.put(3, "C");
//        }, "BB").start();


    }
}
