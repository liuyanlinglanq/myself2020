package com.lyl.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *
 * ArrayBlockingQueue：是一个基于数组的有界阻塞队列,该队列按FIFO(先进先出)原则对元素进行排序
 * LinkedBlockingQueue: 是一个基于链表的有界阻塞队列,该队列按FIFO(先进先出)原则对原始进行排序,其吞吐量一般要高于ArrayBlockingQueue
 * SynchronousQueue: 一个不存储元素的阻塞队列.每个插入操作必须等到一个另一个线程调用移出操作,否则插入操作将一直阻塞.其吞吐量一般要高于
 */
public class BlockingQueueDemo {

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        List list = new ArrayList<>();

        //阻塞队列,数组阻塞队列,一共可以放3个;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        //抛出异常的插入,add,删除,remove,检查element
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // java.lang.IllegalStateException: Queue full
        // System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //java.util.NoSuchElementException
        //System.out.println(blockingQueue.remove());

        //java.util.NoSuchElementException
//        System.out.println(blockingQueue.element());

        //特殊值,不抛出异常,插入offer可以就true/否则false,删除poll,检查peek(窥视),有就拿到值,没有就是返回null
        System.out.println(blockingQueue.offer("offerA"));
        System.out.println(blockingQueue.offer("offerB"));
        System.out.println(blockingQueue.offer("offerC"));
        //false
        System.out.println(blockingQueue.offer("offerD"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //null
        System.out.println(blockingQueue.poll());

        //null
        System.out.println(blockingQueue.peek());

        //阻塞,put,take,满了 put就一直阻塞;空了,take就一直阻塞
        blockingQueue.put("putA");
        blockingQueue.put("putB");
        blockingQueue.put("putC");
        System.out.println("============");
//        blockingQueue.put("putD");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        System.out.println("============");
//        blockingQueue.take();

        //超时退出,offer/poll
        System.out.println(blockingQueue.offer("offerTimeA", 1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("offerTimeB", 1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("offerTimeC", 1, TimeUnit.SECONDS));
        //阻塞5秒,false
        System.out.println(blockingQueue.offer("offerTimeD", 5, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        //阻塞5秒,null
        System.out.println(blockingQueue.poll(5, TimeUnit.SECONDS));
    }


}
