package com.lyl.concurrent.volatiledemo;

/**
 * description T1
 *
 * @author liuyanling
 * @date 2020-11-22 16:02
 */
public class T1 {

    private volatile int num = 0;

    public  void add() {
        num++;
    }
}
