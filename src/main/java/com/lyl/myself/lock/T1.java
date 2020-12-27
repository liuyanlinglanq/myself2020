package com.lyl.myself.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * description T1
 *
 * @author liuyanling
 * @date 2020-11-26 23:47
 */
public class T1 {

    volatile int n = 0;

    public void add() {
        n++;
    }

    public static void main(String[] args) {
        //非公平锁
        ReentrantLock lock = new ReentrantLock();
        //true:公平锁
        ReentrantLock fairLock = new ReentrantLock(true);
    }
}
