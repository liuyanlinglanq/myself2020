package com.lyl.concurrent.a007thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步和可重入锁
 *
 * @author liuyanling
 * @date 2020-12-09 20:17
 */
public class SynchronizedAndReentrantDemo {

    public static void main(String[] args) {

        synchronized (SynchronizedAndReentrantDemo.class) {

        }

        new ReentrantLock();

    }
}

///Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home\bin\javap -c SynchronizedAndReentrantDemo
//Compiled from "SynchronizedAndReentrantDemo.java"
//public class SynchronizedAndReentrantDemo {
//  public SynchronizedAndReentrantDemo();
//    Code:
//       0: aload_0
//       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//       4: return
//
//  public static void main(java.lang.String[]);
//    Code:
//       0: ldc           #2                  // class com/lyl/myself/a007thread/SynchronizedAndReentrantDemo
//       2: dup
//       3: astore_1
//       4: monitorenter
//       5: aload_1
//       6: monitorexit
//       7: goto          15
//      10: astore_2
//      11: aload_1
//      12: monitorexit
//      13: aload_2
//      14: athrow
//      15: new           #3                  // class java/util/concurrent/locks/ReentrantLock
//      18: dup
//      19: invokespecial #4                  // Method java/util/concurrent/locks/ReentrantLock."<init>":()V
//      22: pop
//      23: return
//    Exception table:
//       from    to  target type
//           5     7    10   any
//          10    13    10   any
//}
//
//Process finished with exit code 0

