package com.lyl.concurrent.distruptor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockStrategy implements WaitStrategy {
//    private AnotherDistruptor distruptor;
//    private ReentrantLock lock = new ReentrantLock();
//    private Condition condition = lock.newCondition();
//
//    @Override
//    public int waitFor(int index) {
//        // todo
//        return 1;
//    }
//
//    @Override
//    public void setDistruptor(AnotherDistruptor distruptor) {
//        this.distruptor = distruptor;
//    }
//
//    @Override
//    public void signalAll() {
//        lock.lock();
//        try {
//            condition.signalAll();
//        } finally {
//            lock.unlock();
//        }
//    }

    private AnotherDistruptor distruptor;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public int waitFor(int index) {
        int barrier;
        if ((barrier = distruptor.getMaxProducerIndex()) >= index && distruptor.isAvailable(index)) {
            return barrier;
        }

        try {
            lock.lock();
            while ((barrier = distruptor.getMaxProducerIndex()) < index || !distruptor.isAvailable(index)) {
                condition.await();
            }
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
        return barrier;
    }

    @Override
    public void setDistruptor(AnotherDistruptor distruptor) {
        this.distruptor = distruptor;
    }

    @Override
    public void signalAll() {
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}