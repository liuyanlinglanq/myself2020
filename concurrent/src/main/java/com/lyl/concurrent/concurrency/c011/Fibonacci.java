package com.lyl.concurrent.concurrency.c011;

import java.util.Arrays;

public class Fibonacci {

    /**
     * 局部变量不存在数据竞争的
     * 当多个线程同时访问同一数据，并且至少有一个线程会写这个数据的时候，
     * 如果我们不采取防护 措施，那么就会导致并发 Bug，对此还有一个专业的术语，叫做数据竞争(Data Race)
     * @param n
     * @return
     */
    public static int[] fibonacci(int n) {
        int[] r = new int[n];
        // 初始化第一、第二个数
        r[0] = r[1] = 1;
        for (int i = 2; i < n; i++) {
            r[i] = r[i - 1] + r[i - 2];
        }
        return r;
    }

    public static void main(String[] args) {
        int a = 7;
        int[] b = fibonacci(a);
        int[] c = b;
        Arrays.stream(c).forEach(e-> System.out.print(e+" "));
    }
}
