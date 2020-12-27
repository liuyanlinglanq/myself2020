package com.lyl.myself.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 不安全的集合
 *
 * @author liuyanling
 * @date 2020-11-25 23:51
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
//        listNotSafe();
//       setNotSafe();
        mapNotSafe();

    }

    private static void mapNotSafe() {
        //        Map<String, String> map = new HashMap<>();
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        // 3个线程,添加数据
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void setNotSafe() {
        Set<String> list1 = new HashSet<>();
        Set<String> list2 = Collections.synchronizedSet(new HashSet<>());
        Set<String> list = new CopyOnWriteArraySet<>();
        // 3个线程,添加数据
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    private static void listNotSafe() {
        //打印list
        List<String> list1 = Arrays.asList("a", "b", "c");
        list1.forEach(System.out::println);

        //集合接口
//        Collection;
        //集合类
//        Collections;

        //线程不安全,单线程不会出问题,多线程问题会比较多

//        List<String> list = new ArrayList<>();
//        Vector<String> list = new Vector<>();
        //包一个安全的
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        // 3个线程,添加数据
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        //[4e5482b0]
        //[4e5482b0, c8a7a554]
        //[4e5482b0, c8a7a554, d3c6a0e1]

        //[0338a7b6, 68ab288c]
        //[0338a7b6, 68ab288c]
        //[0338a7b6, 68ab288c]

        //java.util.ConcurrentModificationException

        /**
         * 1. 故障现象
         *  java.util.ConcurrentModificationException
         *
         * 2. 导致原因
         *  并发争抢修改导致,参考:花名册签名情况
         *  一个人正在写入,另外一个同学过来抢夺,导致数据不一致异常.并发修改异常
         *
         * 3. 解决方案
         *    3.1 用vector(加锁解决),vector是SDK1.0,ArrayList 是SDK1.2,Vector加锁,并发下降
         *    3.2 Collections.synchronizedList(new ArrayList<>()) 构建一个同步的list
         *    3.3 CopyOnWriteArraylList
         * 4. 优化建议(同样的错误不犯第二次)
         */}
}
