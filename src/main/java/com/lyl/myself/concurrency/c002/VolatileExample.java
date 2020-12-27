package com.lyl.myself.concurrency.c002;


import cn.hutool.core.lang.Assert;

/**
 * volatile
 */
public class VolatileExample {
    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v = true) {
            System.out.println(x);
        }
    }

    int y = 10;

    public void testSynchronized() {
        synchronized (this) { // 此处自动加锁
            // y 是共享变量, 初始值 =10
            if (this.y < 12) {
                this.x = 12;
            }
        } // 此处自动解锁
    }

    static int var = 0;

    public void testThreadStart() {
        Thread threadA = new Thread(() -> {
            Thread threadB = new Thread(() -> {
                //主线程调用 B.start() 之前
                //所以对共享变量的修改,此处皆可见
                //此例中,var==77
                if (var == 77) {
                    System.out.println(var);
                }
            });
            var = 77;
            threadB.start();

        });
        threadA.start();

    }


    public static void main(String[] args) {
        //2. volatile 变量规则 3. 传递性
//        VolatileExample example = new VolatileExample();
//        example.writer();
//        example.reader();


        //start
//        VolatileExample example = new VolatileExample();
//        example.testThreadStart();


        // 5. 线程 start() 规则
        // 主线程 A 启动子线程 B 后，子线程 B 能够看到主线程在启动子线程 B 前的操作。
//        Thread threadB = new Thread(() -> {
//            //主线程调用 B.start() 之前
//            //所以对共享变量的修改,此处皆可见
//            //此例中,var==77
//            if (var == 77) {
//                System.out.println("主线程操作的var已经变成77了");
//            } else {
//                System.out.println("主线程操作的var还没有变成77");
//            }
//        });
//        var = 77;
//        threadB.start();
//
//        System.out.println("   " + var);

        //6. 线程 join() 规则
        //	线程等待的
        //	主线程 A 等待子线程 B 完成,当子线程 B 完成后,主线程能够看到子线程的操作。也就是B修改的共享变量,A可见
        Thread threadB = new Thread(() -> {
            //修改了共享变量
            var = 60;
        });
        threadB.start();
        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(var);

    }
}
