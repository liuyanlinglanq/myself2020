package com.lyl.myself.jvm.oom;

import java.util.Random;

/**
 * java堆空间溢出
 * 利用大对象,若一个对象,就有10M了,那一定会堆空间溢出
 * -Xms5M -Xmx10M 调节堆空间大小,最小5M,最大10M;
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *
 * @author liuyanling
 * @date 2020-12-27 18:19
 */
public class JavaHeapSpaceDemo {

    public static void main(String[] args) {

        //大对象
//        Byte[] a = new Byte[15*1024*1024];

        //大对象
        String str = "haha";
        int i = 0;
        while (true) {
            System.out.print(i++ + " ");
            //str的大小要翻翻的往上涨,很快就会变成大对象 (注意这里是str=str+str+随机数+随机数),下面的intern加不加都会很快就溢出,本来以为是靠占满常量池来取胜的;
            str += str + new Random().nextInt(1111111111) + new Random().nextInt(2222222);
            str.intern();
        }

    }
}
