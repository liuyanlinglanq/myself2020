package com.lyl.myself.jvm.py;

/**
 * description V
 *
 * @author liuyanling
 * @date 2021-04-20 20:33
 */
public class V {

    static V global_v;

    public void aMethod() {
        V v = bMethod();
        cMethod();
    }

    public V bMethod() {
        V v = new V();
        return v;
    }

    public void cMethod() {
        global_v = new V();
    }

    public static void main(String[] args) {
        System.out.println(global_v);
        V v = new V();
        v.aMethod();
        System.out.println(global_v);
    }
}
