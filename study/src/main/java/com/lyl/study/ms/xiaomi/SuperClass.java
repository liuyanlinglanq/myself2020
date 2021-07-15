package com.lyl.study.ms.xiaomi;

/**
 * 通过子类引用父类的静态字段，不会导致子类初始化
 */
public class SuperClass {
    static {
        System.out.println("SuperClass init....");
    }

    public static int value = 123;
}


class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init....");
    }
}

class ConstClass {
    static {
        System.out.println("ConstClass init....");
    }

    public static final String MM = "hello Franco";
}

