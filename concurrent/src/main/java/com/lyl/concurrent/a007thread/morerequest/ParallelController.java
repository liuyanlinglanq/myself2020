package com.lyl.concurrent.a007thread.morerequest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * 1、串行处理多请求，代码很简单，响应时长是每个请求时长的总和，很稳定但是效率不高
 * 2、使用java8并行处理，代码很简单，响应时长很不稳定，效率也不高，不建议使用
 * 3、使用多线程处理时，代码复杂，响应时长为单个请求中时长最长的那个，效率很高，推荐使用
 */
@Slf4j
public class ParallelController {


    public static void main(String[] args) throws IOException {
        ParallelController controller = new ParallelController();

//        Scanner input = new Scanner(System.in);
//        while (true) {
//            String n = input.next();
//            switch (n) {
//                case "test1":
//                    controller.test1();
//                    break;
//                case "test2":
//                    controller.test2();
//                    break;
//                case "test3":
//                    controller.test3();
//                    break;
//                default:
//            }
//
//            if (n.equals("quit")) {
//                break;
//            }
//        }

//        for (int i = 1; i <= 30; i++) {
//            new Thread(() -> {
//                controller.test1();
//            }, "test1" + i).start();
//        }
//
//        for (int i = 1; i <= 30; i++) {
//            new Thread(() -> {
//                controller.test2();
//            }, "test2" + i).start();
//        }
//
//        for (int i = 1; i <= 30; i++) {
//            new Thread(() -> {
//                controller.test3();
//            }, "test3" + i).start();
//        }

        controller.test1();
        log.info("==============================");
        controller.test4();
        log.info("==============================");
        controller.test5();
        log.info("==============================");
        controller.test6();
    }

    /**
     * 串行(传统请求)
     */
    public void test1() {
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        IntStream.range(0, 10).forEach(index -> {
            list.add(request(index % 3));
        });
        log.info("串行，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);
    }


    /**
     * 并行请求
     */
    public void test2() {
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        IntStream.range(0, 3).parallel().forEach(index -> {
            list.add(request(index));
        });
        log.info("java8并行，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);
    }

    /**
     * 多线程请求
     */
    public void test3() {
        long start = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(3); // 开启3个线程
        IntStream.range(0, 3).forEach(index -> {
            Future<String> task = executor.submit(() -> request(index));
            futureList.add(task);
        });
        for (Future<String> future : futureList) {
            try {
                // 如果任务执行完成，future.get()方法会返回Callable任务的执行结果。
                // future.get()方法会产生阻塞，所有线程都阻塞在这里，当获取到一个结果后，才执行下一个
                list.add(future.get());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();

        log.info("多线程，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);
    }

    /**
     * 多线程请求(带超时时间)
     * future.get(timeout, unit)：在指定的时间内会等待任务执行，超时则抛异常。
     * 任务执行的时间是获取到结果的时长。
     * 由于每个任务是同时执行的,但是获取结果时，是阻塞的，也就是串行获取的，
     * 所以每个任务获取结果的时长 = 当前任务请求时长 - 上一个任务请求时长。
     * <p>
     * 由此可以计算出任务a时长是1s，任务b是2-1=1s，任务c是10-2=8s。至于总时长5s = 任务b获取结果用时2s + 超时时间3s
     * <p>
     * 结论：当只有一个任务时，超时时间有效，当多个任务执行时，超时时间无效
     */
    public void test4() {
        long start = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 开启3个线程
        IntStream.range(0, 10).forEach(index -> {
            Future<String> task = executor.submit(() -> request(index % 3));
            futureList.add(task);
        });
        for (int i = 0; i < futureList.size(); i++) {
            Future<String> future = futureList.get(i);
            try {
                // future.get(timeout, unit)：在指定的时间内会等待任务执行，超时则抛异常。
                String result = future.get(5, TimeUnit.SECONDS);
                log.info("结果：{}", result);
                list.add(result);
            } catch (TimeoutException e) {
                log.info("task {}，超时", i);
                // 强制取消该任务
                future.cancel(true);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();

        log.info("多线程，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);
//        list.forEach(System.out::println);
    }

    /**
     * 多线程请求(带超时时间)
     * 虽然代码复杂一点，但是效果基本达到了，不过有一点，开启的线程的翻了一倍，对内存消耗比较大
     */
    public void test5() {
        long start = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 开启3个线程
        IntStream.range(0, 10).forEach(index -> {
            Future<String> task = executor.submit(() -> request(index % 3));
            futureList.add(task);
        });
        for (int i = 0; i < futureList.size(); i++) {
            Future<String> future = futureList.get(i);
            ExecutorService executor2 = Executors.newSingleThreadExecutor();
            int j = i;
            executor2.execute(() -> {
                try {
                    // 在指定的时间内会等待任务执行，超时则抛异常
                    long start1 = System.currentTimeMillis();
                    String result = future.get(4, TimeUnit.SECONDS);
                    log.info("结果：{}，用时：{}", result, System.currentTimeMillis() - start1);
                    list.add(result);
                } catch (TimeoutException e) {
                    log.info("task{}，超时", j);
                    future.cancel(true);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }

        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();

        while (true) {
            // 将future.get放入子线程后，由于不会阻塞，所以就直接运行到下面。需要通过判断所有线程是否结束来获取最终结果
            if (executor.isTerminated()) {
                log.info("多线程，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);
                break;
            }
        }

//        list.forEach(System.out::println);
    }

    /**
     * 多线程请求(带超时时间)
     */
    public void test6() {
        long start = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 开启3个线程
        List<Callable<String>> callableList = new ArrayList<>();
        IntStream.range(0, 10).forEach(index -> {
            callableList.add(() -> request(index % 3));
        });
        try {
            log.info("开始执行");
            long start1 = System.currentTimeMillis();
            // invokeAll会阻塞。必须等待所有的任务执行完成后统一返回，这里的超时时间是针对的所有tasks，而不是单个task的超时时间。
            // 如果超时，会取消没有执行完的所有任务，并抛出超时异常
            List<Future<String>> futureList = executor.invokeAll(callableList, 4, TimeUnit.SECONDS);
            log.info("执行完，用时：{}", System.currentTimeMillis() - start1);
            for (int i = 0; i < futureList.size(); i++) {
                Future<String> future = futureList.get(i);
                try {
                    list.add(future.get());
                } catch (CancellationException e) {
                    log.info("超时任务：{}", i);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (InterruptedException e1) {
            log.info("线程被中断");
        }

        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();

        log.info("多线程，响应结果：{}，响应时长：{}", Arrays.toString(list.toArray()), System.currentTimeMillis() - start);

//        list.forEach(System.out::println);
    }

    private String request(int index) {
        ParallelService parallelService = new ParallelService();

        if (index == 0) {
            return parallelService.requestA();
        }
        if (index == 1) {
            return parallelService.requestB();
        }
        if (index == 2) {
            return parallelService.requestC();
        }
        return null;
    }

}