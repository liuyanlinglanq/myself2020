package com.lyl.jvm.py;

/**
 * description StackOnTest
 *
 * @author liuyanling
 * @date 2021-04-20 20:36
 */
public class StackOnTest {

    public static void alloc() {
        //创建byte数组，放入值
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        StackOnTest onTest = new StackOnTest();
        //循环一亿次，调用alloc，在一个线程的占中，
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }
}
