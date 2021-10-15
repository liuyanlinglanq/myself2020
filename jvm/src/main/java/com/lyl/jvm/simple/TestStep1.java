package com.lyl.jvm.simple;

import org.junit.Test;

/**
 * description TestStep1
 *
 * @author liuyanling
 * @date 2021-10-13 14:50
 */
public class TestStep1 {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        System.out.println(f(40)); //165580141
        long end = System.currentTimeMillis();
        System.out.println(end - start); //463
    }

    private long f(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(n + "不能小于1");
        }
        if (n < 3) {
            return n;
        }

        return f(n - 2) + f(n - 1);
    }


}
