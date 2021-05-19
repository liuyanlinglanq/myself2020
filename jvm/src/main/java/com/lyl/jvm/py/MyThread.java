package com.lyl.jvm.py;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class MyThread {

    public static void main(String[] args) {
        int N = 8;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new AthletesThread(barrier).start();
    }
    static class AthletesThread extends Thread{
        //volatile修饰符用来保证其它线程读取的总是该变量的最新的值
        public static volatile boolean exit = false;
        private static int sort = 0;
        private CyclicBarrier cyclicBarrier;
        public synchronized static int getSort() {
            if (sort>=3) { //获取排名，大于n名结束
                exit = true;
                return 0;
            }
            return ++ sort;
        }

        public static Boolean getIsExit() {
            return exit;
        }

        public AthletesThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public String getNowName() {
            return "运动员"+ Thread.currentThread().getName();
        }

        @Override
        public void run() {
            Random r = new Random(System.nanoTime());
            String NowName = Thread.currentThread().getName();
            //开跑准备
            System.out.println(NowName +"开跑准备");
            try {
                int readyTime = r.nextInt(1000);
                Thread.sleep(readyTime);      //以睡眠来模拟准备操作
                System.out.println(NowName + "准备完毕，耗时"+ readyTime + "等待其他运动员准备完毕");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            int runTime = r.nextInt(10);
            long startTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            int nowSort = 0;
            while(!getIsExit()){
//                    Thread.sleep(1);
                if (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() >= startTime + runTime) {
                    nowSort = getSort();
                    if (nowSort > 0) { //有名次运动员打印
                        System.out.println(NowName +"跑完全程：" + runTime + "ms,排名：" + nowSort);
                    }
                    break;
                }
            }
            //被终止的运动员打印
            if (nowSort == 0) {
                System.out.println(NowName +"未跑完全程：" + runTime + "已终止");
            }
        }
    }
}