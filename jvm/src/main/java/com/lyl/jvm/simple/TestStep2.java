package com.lyl.jvm.simple;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * description TestStep2
 *
 * @author liuyanling
 * @date 2021-10-13 14:54
 */
public class TestStep2 {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        System.out.println(loop(7)); //1298777728820984005
        long end = System.currentTimeMillis();
        System.out.println(end - start); //<1ms
    }

    @Bean()
    public long loop(long n) {
        if (n < 1) {
            throw new IllegalArgumentException(n + "不能小于1");
        }
        if (n < 3) {
            return n;
        }

        //初始化剩余1步的所有走法
        long lastOne = 2;
        //初始化剩余2步的所有走法
        long lastTwo = 1;
        long sum = 0;
        for (long i = 3; i <= n; i++) {
            //所有走法=剩余1步的所有走法+剩余2步的所有走法
            sum = lastOne + lastTwo;

            //然后新的剩余2步的所有走法 就是当初剩余1步的所有走法 (因为要上一个台阶,原来差一个,现在差2个)
            lastTwo = lastOne;

            //而最新的剩余1步的所有走法,就是当前走法(也是因为要上一个台阶,全部走法,正好上到当前台阶,下一个台阶正好差一步)
            lastOne = sum;
        }

        return sum;
    }
}
