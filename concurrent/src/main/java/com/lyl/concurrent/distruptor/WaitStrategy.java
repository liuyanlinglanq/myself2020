package com.lyl.concurrent.distruptor;

public interface WaitStrategy {
    int waitFor(int index); // 等待指定位置的元素可用
    void setDistruptor(AnotherDistruptor distruptor);
    void signalAll();
}