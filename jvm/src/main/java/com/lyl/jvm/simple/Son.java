package com.lyl.jvm.simple;

/**
 * description Son
 *
 * @author liuyanling
 * @date 2021-10-12 19:30
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    public Son() {
        super();
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 1;
    }

    public static void main(String[] args) {
//        Son s1 = new Son();
//        System.out.println();
//        Son s2 = new Son();
//        Father s1 = new Father();
//        System.out.println();
    }
}
