package com.lyl.jvm.gc;

import lombok.SneakyThrows;

/**
 * description HelloGC
 *
 * @author liuyanling
 * @date 2020-12-12 13:15
 */
public class HelloGC {
    //-XX:+PrintGCDetails -XX:MetaspaceSize=256M
    //VM:options -XX:+PrintCommandLineFlags :
    // -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
    //VM:options -Xms128M -Xmx4096M -Xss1024k -XX:MetaspaceSize=1024M -XX:+PrintCommandLineFlags -XX:+PrintGCDetails
    // -XX:+UseSerialGC:
    // -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=4294967296-XX:MetaspaceSize=1073741824
    // -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:ThreadStackSize=1024 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("******hello gc****");

        Byte[] bytes = new Byte[2 * 1024 * 1024];
//        Thread.sleep(Integer.MAX_VALUE);


//        //java虚拟机中的内存总量,我电脑8G内存,8G/64=128M; 123M
//        System.out.println( Runtime.getRuntime().totalMemory()/1024/1024);
//        //java虚拟机试图使用的最大内存量;8G/4=2G; 1820M
//        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
    }
}

//Heap
//        PSYoungGen      total 2560K, used 2048K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
//        eden space 2048K, 100% used [0x00000007bfd00000,0x00000007bff00000,0x00000007bff00000)
//        from space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
//        to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
//        ParOldGen       total 7168K, used 0K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
//        object space 7168K, 0% used [0x00000007bf600000,0x00000007bf600000,0x00000007bfd00000)
//        Metaspace       used 3071K, capacity 4496K, committed 4864K, reserved 1056768K
//class space    used 335K, capacity 388K, committed 512K, reserved 1048576K

//Heap
//        def new generation   total 2880K, used 2051K [0x00000007bf600000, 0x00000007bf950000, 0x00000007bf950000)
//        eden space 2368K,  86% used [0x00000007bf600000, 0x00000007bf800f08, 0x00000007bf850000)
//        from space 512K,   0% used [0x00000007bf850000, 0x00000007bf850000, 0x00000007bf8d0000)
//        to   space 512K,   0% used [0x00000007bf8d0000, 0x00000007bf8d0000, 0x00000007bf950000)
//        tenured generation   total 6848K, used 0K [0x00000007bf950000, 0x00000007c0000000, 0x00000007c0000000)
//        the space 6848K,   0% used [0x00000007bf950000, 0x00000007bf950000, 0x00000007bf950200, 0x00000007c0000000)
//        Metaspace       used 3097K, capacity 4496K, committed 4864K, reserved 1056768K
//class space    used 339K, capacity 388K, committed 512K, reserved 1048576K

