package com.lyl.myself.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * description CASDemo
 * CAS:比较并交换
 *
 * @author liuyanling
 * @date 2020-11-25 10:04
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 6) + " \t current data:" + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 8) + " \t current data:" + atomicInteger.get());

        //结果:
        //true 	 current data:6
        //false 	 current data:6

        atomicInteger.getAndIncrement();

    }
}
