package com.lyl.myself.jvm.py;

import java.util.ArrayList;
import java.util.List;

/**
 * description HeadOOMDemo
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 * @author liuyanling
 * @date 2021-04-21 00:32
 */
public class HeadOOMDemo {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
