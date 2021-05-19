package com.lyl.concurrent.distruptor;

public interface EventFactory<E> {
    E getEvent();
}