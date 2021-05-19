package com.lyl.concurrent.concurrency.c012;

/**
 * 封装 value
 * 对于 这些不会发生变化的共享变量，建议你用 final 关键字来修饰。
 */
public class Counter {

    private long value;

    synchronized long get() {
        return value;
    }

    synchronized long addOne() {
        return ++value;
    }
}
