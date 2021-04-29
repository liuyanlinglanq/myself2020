package com.lyl.myself.distruptor;

public interface EventFactory<E> {
    E getEvent();
}