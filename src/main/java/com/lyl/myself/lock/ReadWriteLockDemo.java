package com.lyl.myself.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * <p>
 * 多个线程同时对一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行;
 * 但是
 * 如果有一个线程想去写共享资源,就不应该再有其他线程可以对该资源进行读/写
 * 小总结:
 * 读-读 能共存
 * 读-写,写-写,不能共存
 * <p>
 * 写操作: 特点是原子 + 独占,任务要执行完整,独占只有他一个人做; 整个过程必须是一个完整的统一体,中间不许被分割,被打断;
 * <p>
 * <p>
 * <p>
 * 资源类MyCache,有一个volatile的资源Map; 以前用的可重入锁,但是他是独占的,本次要的读写锁;
 * 加一个变量 Lock ,可重入锁; 读写锁;
 * <p>
 * 写一个put方法,用于放入key/value; HashMap key比较重要,value不太重要;写入/写入完成均打印;并模拟网络用途,好看到效果,休息300毫秒; put之前加写锁/解锁;
 * (所以预期,写入开始和写入完成是在一起的,不可中断的)
 * 写一个get(key),读方法; 读取开始/读取完成,打印并打印结果;并模拟网络用途,好看到效果,休息300毫秒; 读是可以共享的,所以可以有多个线程都在读,可以中断;get之前加读锁/解锁
 * <p>
 * main方法资源类
 * 5个人来写;5个人来读
 * 5个线程分别写入1-5;
 * 5个线程来读,读取1-5;  不加锁的话顺序完全乱掉;加可重入锁;
 *
 * @author liuyanling
 * @date 2020-11-28 12:44
 */
public class ReadWriteLockDemo {

    //读写锁,写资源独占;读资源随意;需要有两个方法,读/写(读取/更新)
    //这里和自旋锁不一样,重点不是加锁,(循环加锁)
    //这里的重点是两个方法,读共享,写独占,所以写要加独占锁,读要加写锁;然后多个线程读写顺序无误;
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //5写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        //5读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }

}

class MyCache {

    //只有volatile,没有锁
    private volatile Map<String, Object> map = new HashMap<>();
    //加可重入锁,完全写1-5,读1-5,顺序刚刚地
    // private Lock lock = new ReentrantLock();
    //加读写锁,顺序不一定,但是写一定是开始结束一对一起出现;读可以乱序;
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " \t 写入开始 " + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " \t 写入结束 " + key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " \t 读取开始 " + key);
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + " \t 读取结束 " + key + " \t" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }

    }

}
