package com.lyl.concurrent.distruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * description AnotherDistruptor
 *
 * @author liuyanling
 * @date 2021-03-25 19:08
 */
public class AnotherDistruptor<E, V> {

//    private RingBuffer<E> ringBuffer;
//    private Sequencer sequencer;
//    private boolean[] publishState;
//    private final static int bufferSize = 1024;
//
//    public int getNextWriteIndex() {
//        int writeIndex = sequencer.getNextWriteIndex();
//        return writeIndex;
//    }
//    public int getNextConsumeIndex() {
//        int readIndex = sequencer.getNextReadIndex();
//        return readIndex;
//    }
//    public E getEvent(int index) {
//        return ringBuffer.getEvent(index);
//    }
//    public void publish(int index) {
//        publishState[index % bufferSize] = true;
//    }
//    public boolean isAvailable(int index) {
//        return publishState[index % bufferSize];
//    }


    private RingBuffer<E> ringBuffer;
    private Sequencer sequencer;
    private boolean[] publishState;

    private WaitStrategy waitStrategy;
    private Worker<E, V>[] workers;
    private Producer<E, V>[] producers;
    private ExecutorService service;
    private int bufferSize;

    public AnotherDistruptor(
            int bufferSize, Producer<E, V>[] producers,
            Consumer<E>[] consumers,
            EventFactory<E> eventFactory,
            WaitStrategy waitStrategy) {
        this.bufferSize = bufferSize;
        this.ringBuffer = new RingBuffer<>(bufferSize);
        this.sequencer = new Sequencer();
        this.publishState = new boolean[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            ringBuffer.setEvent(eventFactory.getEvent(), i);
            publishState[i] = false;
        }
        this.waitStrategy = waitStrategy;
        waitStrategy.setDistruptor(this);
        int size = consumers.length;
        this.workers = new Worker[size];
        for (int i = 0; i < size; i++) {
            this.workers[i] = new Worker(consumers[i], this);
        }
        this.producers = producers;
        for (Producer<E, V> producer : producers) {
            producer.setDistruptor(this);
        }
    }

    public void start() {
        int size = workers.length;
        service = new ThreadPoolExecutor(
                size, size, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (Worker worker : workers) {
            service.submit(worker);
        }
    }

    public void stop() {
        service.shutdown();
    }

    public static class Worker<E, T> extends Thread {
        private Consumer<E> consumer;
        private AnotherDistruptor<E, T> distruptor;
        private int workerIndex = -1;

        public Worker(Consumer<E> consumer, AnotherDistruptor<E, T> distruptor) {
            this.consumer = consumer;
            this.distruptor = distruptor;
        }

        public void run() {
            while (true) {
                workerIndex = distruptor.getNextConsumeIndex();
                consumer.consume(distruptor.getEvent(workerIndex));
            }
        }

        public int getConsumerIndex() {
            return workerIndex;
        }
    }

    public int getNextWriteIndex() {
        int writeIndex = sequencer.getNextWriteIndex();
        System.out.println(Thread.currentThread().getName() + "get write index: " + writeIndex);
        int minReadIndex;
        while ((minReadIndex = getMinConsumerIndex()) <= writeIndex - bufferSize) {
            LockSupport.parkNanos(1);
        }
        return writeIndex;
    }

    public int getNextConsumeIndex() {
        int readIndex = sequencer.getNextReadIndex();
        System.out.println(Thread.currentThread().getName() + "get index: " + readIndex);
        int cachedAvailableIndex = Integer.MIN_VALUE;
        while (!publishState[readIndex % bufferSize] || cachedAvailableIndex < readIndex) {
            cachedAvailableIndex = waitStrategy.waitFor(readIndex);
        }

        publishState[readIndex % bufferSize] = false;
        System.out.println(Thread.currentThread().getName() + "set state false: " + readIndex);
        return readIndex;
    }

    public E getEvent(int index) {
        return ringBuffer.getEvent(index);
    }

    private int getMinConsumerIndex() {
        int min = Integer.MAX_VALUE;
        for (Worker worker : workers) {
            int workerIndex = worker.getConsumerIndex();
            if (workerIndex < min) {
                min = workerIndex;
            }
        }
        return min;
    }

    public int getMaxProducerIndex() {
        int max = 0;
        for (Producer producer : producers) {
            int workerIndex = producer.getProducerIndex();
            if (workerIndex > max) {
                max = workerIndex;
            }
        }
        return max;
    }

    public void publish(int index) {
        publishState[index % bufferSize] = true;
        waitStrategy.signalAll();
    }

    public boolean isAvailable(int index) {
        return publishState[index % bufferSize];
    }
}
