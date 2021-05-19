package com.lyl.concurrent.a007thread;

import org.junit.platform.commons.util.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版的生产者
 *
 * @author liuyanling
 * @date 2020-12-09 20:17
 */
public class ProductCustomerBlockingQueueDemo {


    public static void main(String[] args) throws InterruptedException {

        MyResource myResource = new MyResource(new LinkedBlockingQueue<>(10));
        //一个生产线程,生产;
        new Thread(() -> {
            try {
                myResource.product();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "product").start();

        //一个消费线程,消费
        new Thread(() -> {
            try {
//                TimeUnit.SECONDS.sleep(3);
                myResource.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        //main线程,决定要停掉生产线,同时也没有消费了
        TimeUnit.SECONDS.sleep(5);
        myResource.stop();
    }
}


/**
 * 资源类
 * 生产一个/消费一个,使用阻塞队列来控制是否阻塞,不需要用lock的await和signalAll
 */
class MyResource {

    //默认一开始,生产和消费都是可以执行的
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    //一个阻塞队列
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(Thread.currentThread().getName() + " " + blockingQueue);
    }

    /**
     * 生产
     */
    public void product() throws Exception {

        String data;
        boolean result;
        //等于true可以执行
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            result = blockingQueue.offer(data, 2, TimeUnit.SECONDS);

            if (result) {
                System.out.println(Thread.currentThread().getName() + " 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + " 插入队列" + data + "失败");
            }

            //1秒钟生产一个
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName() + " 叫停了,FLAG变成false了");

    }

    /**
     * 消费
     */
    public void consumer() throws Exception {

        //等于true可以执行
        String data;
        while (FLAG) {
            data = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (StringUtils.isNotBlank(data)) {
                System.out.println(Thread.currentThread().getName() + " 消费队列,获取数据" + data + "成功");
            } else {
                this.FLAG = false;
                System.out.println(Thread.currentThread().getName() + " 消费队列,获取数据失败,可以停了");
                return;
            }
        }

    }

    /**
     * 停止
     */
    public void stop() {
        this.FLAG = false;
    }
}


