package com.lyl.myself.distruptor;

public interface Consumer<T> {
    void consume(T event);
}
