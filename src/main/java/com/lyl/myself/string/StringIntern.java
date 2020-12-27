package com.lyl.myself.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * description StringIntern
 *
 * @author liuyanling
 * @date 2020-11-29 22:42
 */
public class StringIntern {

    public static void main(String[] args) throws InterruptedException {

        //好可爱 好可爱 true    堆,堆引用 true
        //java java false     java堆,不是第一次,常量池;false
        //天天 天天 false   name4=天天,表示常量池有一个"天天"了;  name3在堆上,name3.intern有天天了,在常量池 false;
        //天天 天天 true    常量池=常量池 true;
//
//        String name1 = new StringBuilder("好").append("未来").toString();
//        System.out.println(name1 + " " + name1.intern() + " " + (name1.intern() == name1));
//
//
//        String name2 = new StringBuilder("ja").append("va").toString();
//        System.out.println(name2 + " " + name2.intern() + " " + (name2.intern() == name2));
//
//        //name3 在堆上,name4 在常量池中,期待都是false,因为天天已经在常量池中存在,intern会在加一个
//        String name3 = new StringBuilder("内").append("容云").toString();
//        String name4 = "内" + "容云";
//        System.out.println(name3 + " " + name3.intern() + " " + (name3.intern() == name3));
//        System.out.println(name4 + " " + name4.intern() + " " + (name4.intern() == name3.intern() && name4.intern() == name4));
//
//
//        TimeUnit.SECONDS.sleep(1000);

        //false
        //false
        //true
        //true
        //true
        //true
//        String a1 = new String("a");
//        String a2 = a1.intern();
//        String a3 = "a";
//
//
//        System.out.println(a1.intern() == a1); //false
//        System.out.println(a1 == a3);  //false
//        System.out.println(a2 == a3);  //[[false
//
//
//        String c1 = new String("c") + new String("b");
//        String c2 = c1.intern();
//        String c3 = "cb";
//
//        System.out.println(c1 == c2); //true
//        System.out.println(c1 == c3);//true
//        System.out.println(c2 == c3);//true

//        1,2,3,100,5,10
        final Integer[] intArray = {1, 2, 3, 4, 5};
        intArray[3] = 100;
        Arrays.stream(intArray).forEach(a -> System.out.print(a + ","));

        final int a = 10;
//        a = 20;
        System.out.println(a);
    }
}
