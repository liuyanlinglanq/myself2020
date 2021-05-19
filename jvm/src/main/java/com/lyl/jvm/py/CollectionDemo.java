package com.lyl.jvm.py;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 集合的demo
 *
 * @author liuyanling
 * @date 2021-04-29 10:36
 */
public class CollectionDemo {


    public static void main(String[] args) throws InterruptedException {

//        collectionWrite(10, 10000000);

        printOptimization();
    }

    /**
     * 想要证明,不同集合的write和写的速度,
     * //线程 10, 数量: 10000000
     * //HashTable 3178 4802
     * //HashMap 161 519
     * //ConcurrentHashMap 5985 342
     * //synchronizedMap 2793 4159
     *
     * @param threadCount
     * @param count
     * @throws InterruptedException
     */
    private static void collectionWrite(Integer threadCount, Integer count) throws InterruptedException {
        Map<Integer, Integer> ma1 = new HashMap<>();
        Map<Integer, Integer> map = Collections.synchronizedMap(ma1);

        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    map.put(count, count);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);


        long readStart = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    map.get(j);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long readEnd = System.currentTimeMillis();
        System.out.println(readEnd - readStart);

    }


    /**
     * 多线程打印时间的逐步优化案
     */
    private static void printOptimization() {
        //一开始,多个线程,在run中,直接打印结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(new ThreadLocalDemo01().date22(10));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(new ThreadLocalDemo01().date22(1007));
            }
        }).start();

        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //优化1，多个线程运用线程池复用 (使用线程池,线程池最后需要shutdown)
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(new ThreadLocalDemo01().date22(finalI));
                }
            });
        }
        executorService.shutdown();


    }


}

class ThreadLocalDemo01 {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        String s = simpleDateFormat.format(date);
        return s;
    }

    /**
     * 在多线程服用一个SimpleDateFormat时会出现线程安全问题，执行结果会打印出相同的时间。
     *
     * @param seconds
     * @return
     */
    public String date2(int seconds) {
        Date date = new Date(1000 * seconds);
        String s;
//        synchronized (ThreadLocalDemo01.class) {
//            s = simpleDateFormat.format(date);
//        }
        s = simpleDateFormat.format(date);
        return s;
    }

    //优化2，线程池结合ThreadLocal,在优化2中使用线程池结合ThreadLocal实现资源隔离，线程安全
    public String date22(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }
}

class ThreadSafeFormatter {

    static final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<>();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat();
        dateFormatThreadLocal.set(sdf);
    }
}
