package com.lyl.concurrent.distruptor;

public class RingBuffer<T> {
    private T[] ringbuffer;
 
    public RingBuffer(int bufferSize) {
        this.ringbuffer = (T[])new Object[bufferSize];
    }
 
    public T getEvent(int index) {
        return this.ringbuffer[index % ringbuffer.length];
    }
 
    public void setEvent(T event, int index) {
        this.ringbuffer[index % ringbuffer.length] = event;
    }
}