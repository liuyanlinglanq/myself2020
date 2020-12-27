package com.lyl.myself.concurrency.c008;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue<T> {

    final Lock lock = new ReentrantLock();

    //队列不满,可入队
    final Condition notFull = lock.newCondition();

    //队列不空,可出队
    final Condition notEmpty = lock.newCondition();

    //入队操作,已满,则要等不满;入队成功,要通知可以出队,也就是队列不空;
    void enq(T x) {
        lock.lock();
        try {
            //编程范式,mesa模式专有
            while (full()) {
                notFull.await();
            }
            // 省略入队操作...
            // 入队后, 通知可出队
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队
     *
     * @param x
     */
    void dnq(T x) {
        lock.lock();
        try {
            while (empty()) {
                notEmpty.await();
            }
            //省略出队操作...
            //出队后,通知可入队
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private boolean full() {
        return true;
    }

    private boolean empty() {
        return true;
    }

}
