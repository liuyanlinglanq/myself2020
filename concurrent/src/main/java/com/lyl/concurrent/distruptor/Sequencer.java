package com.lyl.concurrent.distruptor;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequencer {
    private AtomicInteger readIndex = new AtomicInteger(-1);
    private AtomicInteger writeIndex = new AtomicInteger(-1);
 
    public int getNextReadIndex() {
        return readIndex.incrementAndGet();
    }
 
    public int getNextWriteIndex() {
        return writeIndex.incrementAndGet();
    }
}