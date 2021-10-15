package com.lyl.jvm.simple;

import com.alibaba.fastjson.JSONObject;

/**
 * description Exam4
 *
 * @author liuyanling
 * @date 2021-10-13 14:22
 */
public class Exam4 {

    public static void main(String[] args) {
        int i = 1;
        String s = "hello";
        Integer n = 127;
        Integer n1 = 200;
        int[] array = {1, 2, 3, 4, 5};
        MyData my = new MyData();

        System.out.println("i=" + i + " " + "s=" + s + " " + "n=" + n + " " + "n1=" + n1 + " " + "array=" + JSONObject.toJSONString(array) +
                " " + "my.d=" + my.d + " ");
        change(i, s, n, n1, array, my);

        System.out.println("i=" + i + " " + "s=" + s + " " + "n=" + n + " " + "n1=" + n1 + " " + "array=" + JSONObject.toJSONString(array) + " " + "my.d=" + my.d + " ");
    }

    public static void change(int i1, String s1, Integer n1, Integer n2, int[] array1, MyData my1) {
        i1 += 1;
        s1 += "hello";
        n1 += 1;
        n2 += 1;
        array1[0] += 1;
        my1.d += 1;
    }
}

class MyData {
    int d = 1;
}
