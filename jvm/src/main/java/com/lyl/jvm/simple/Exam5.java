package com.lyl.jvm.simple;

/**
 * description Exam5
 *
 * @author liuyanling
 * @date 2021-10-13 16:43
 */
public class Exam5 {
    //成员变量-类变量
    static int s;
    //成员变量-实例变量
    int i;
    int j;

    {
        //同名,就近原则
        int i = 1;
        i++;
        j++;
        s++;
    }

    public void test(int j) {
        j++;
        i++;
        s++;
    }

    public static void main(String[] args) {
        Exam5 obj1 = new Exam5();
        Exam5 obj2 = new Exam5();

        obj1.test(10);
        obj1.test(20);

        obj2.test(30);

        System.out.println("i=" + obj1.i + " " + "j=" + obj1.j + " " + "s=" + obj1.s);
        System.out.println("i=" + obj2.i + " " + "j=" + obj2.j + " " + "s=" + obj2.s);
    }

}
