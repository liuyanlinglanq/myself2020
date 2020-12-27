package com.lyl.myself.concurrency.c002;

public class FinalFieldExample {

    final int x;
    private static Global global = new Global();

    public FinalFieldExample() {
        x = 3;
        global.obj = this;
    }

    public static void main(String[] args) {
        System.out.println(global.obj.x);
//        Thread thread1 = new Thread(() -> {
//            FinalFieldExample f = new FinalFieldExample();
//            System.out.println(f.global.obj.x);
//        });
//        Thread thread2 = new Thread(() -> {
//            FinalFieldExample f = new FinalFieldExample();
//            System.out.println(f.global.obj.x);
//        });
//        thread1.start();
//        thread2.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
