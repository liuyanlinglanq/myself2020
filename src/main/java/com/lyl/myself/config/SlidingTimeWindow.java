package com.lyl.myself.config;

//import com.alibaba.csp.sentinel.slots.block.flow.FlowException;

import java.util.LinkedList;
import java.util.Random;

/**
 * description
 * 滑动时间窗口限流实现
 * 假设某个服务最多只能每秒钟处理100个请求，我们可以设置一个1秒钟的滑动时间窗口
 * 窗口中有10个格子，每个格子100毫秒，每100毫秒移动一次，每次移动都需要记录当前服务请求的次数
 *
 * @author andy
 * @since 2020-07-12 19:33
 */
public class SlidingTimeWindow {


    /**
     * 服务访问次数，可以放在Redis中，实现分布式系统的访问计数
     */
    Long counter = 0L;

    boolean limitFlag = false;

    /**
     * 使用LinkedList来记录滑动窗口的10个格子
     */
    LinkedList<Long> slots = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        SlidingTimeWindow timeWindow = new SlidingTimeWindow();
        new Thread(() -> {
            try {
                timeWindow.canPass();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //模拟请求
        while (true) {
            if (timeWindow.limitFlag) {
//                throw new FlowException("限流了");
                System.out.println("限流了");
            }
            timeWindow.counter++;
            Thread.sleep(new Random().nextInt(15));
        }
    }

    private void canPass() throws InterruptedException {
        while (true) {
            slots.addLast(counter);
            if (slots.size() > 10) {
                slots.removeFirst();
            }
            //比较最后一个和第一个，两者相差100以上就限流
            if ((slots.peekLast() - slots.peekFirst()) > 100) {
                System.out.println(counter + "限流了...");
                limitFlag = true;
            } else {
                limitFlag = false;
            }
            Thread.sleep(100);
        }
    }
}