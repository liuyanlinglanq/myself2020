package com.lyl.concurrent.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 争车位;
 * 同一个资源,多个人抢
 * <p>
 * 3个车位,6个车子; 前3个车子抢到资源,然后释放,下面的就可以占了;
 * <p>
 * semaphore : 3个车位
 * 6个车子,进入,semaphore.acquire() 获取到车位,停车3秒(可以随机),离开;
 * finally 释放资源; semaphore.release()
 * <p>
 * 1 , 2, 3   4  5  6
 * 7  4   8   6  3  4
 * 1 	 进入车位
 * 2 	 进入车位
 * 3 	 进入车位
 * 1 	 休息7秒
 * 3 	 休息4秒
 * 2 	 休息8秒
 * 3 	 休息4秒后离开车库
 * 4 	 进入车位
 * 4 	 休息6秒
 * 1 	 休息7秒后离开车库
 * 5 	 进入车位
 * 5 	 休息3秒
 * 2 	 休息8秒后离开车库
 * 6 	 进入车位
 * 6 	 休息4秒
 * 5 	 休息3秒后离开车库
 * 4 	 休息6秒后离开车库
 * 6 	 休息4秒后离开车库
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        // 只有三个车位,是否公平
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " \t 进入车位" + semaphore.availablePermits());

                    //1- 9秒
                    Integer stopSecond = (int) (Math.random() * 9 + 1);
                    System.out.println(Thread.currentThread().getName() + " \t 休息" + stopSecond + "秒");
                    try {
                        TimeUnit.SECONDS.sleep(stopSecond);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " \t 休息" + stopSecond + "秒后离开车库");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //走了要释放资源
                    semaphore.release();
                }


            }, String.valueOf(i)).start();
        }
    }

}
