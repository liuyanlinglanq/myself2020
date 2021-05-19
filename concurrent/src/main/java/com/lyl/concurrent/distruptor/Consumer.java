package com.lyl.concurrent.distruptor;

public interface Consumer<T> {
    void consume(T event);
}
