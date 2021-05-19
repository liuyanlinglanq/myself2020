package com.lyl.jvm.oom;

/**
 * 栈溢出
 * 虚拟机栈溢出,只要利用递归,死循环的调用递归即可 9844次就可以达到溢出的目的;
 *
 * Exception in thread "main" java.lang.StackOverflowError,他们都是error,是java虚拟机的error(虽然我们平时说是异常)
 *
 * @author liuyanling
 * @date 2020-12-27 18:09
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args) {
        Integer i = 0;
        method1(i);
    }

    private static void method1(Integer i) {
        System.out.println(i++);
        //递归调用
        method1(i);
    }
}
