//package com.lyl.myself.volatiledemo;
//
///**
// * 指令重排
// *
// * @author liuyanling
// * @date 2020-11-22 17:45
// */
//public class ReSortSeqDemo {
//    int a = 0;
//    boolean flag = false;
//
//    public void method01() {
//        //语句1
//        a = 1;
//        //语句2
//        flag = true;
//    }
//
//    /**
//     * 多线程环境中线程交替执行,由于编译器优化重排的存在
//     *
//     */
//    public void method02() {
//        if (flag) {
//            //语句3
//            a = a + 5;
//            if (a == 5) {
//                System.out.println("-------returnValue:" + a);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        ReSortSeqDemo sortSeqDemo = new ReSortSeqDemo();
//        for (int i = 0; i < 1000000; i++) {
//            new Thread(() -> {
//                sortSeqDemo.method01();
//                sortSeqDemo.method02();
//            }, String.valueOf(i)).start();
//        }
//    }
//}
