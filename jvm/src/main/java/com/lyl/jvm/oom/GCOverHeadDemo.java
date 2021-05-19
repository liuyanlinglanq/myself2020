package com.lyl.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * GC overhead limit exceeded ：垃圾回收过头，大量的资源98%用于垃圾回收，少部分的资源2%在干活；
 * 连续多次GC都只回收了不到2%的极端情况下才会抛出。假如不抛出gc overhead limit 错误会发生什么情况呢？
 * GC清理的这么点内存很快就会再次填满，迫使GC再次执行，这样形成恶性循环，CPU使用率一直是100%，而GC却没有任何成果。
 * <p>
 * 生成的大量对象都是强依赖的,GC无法回收,即可出现该问题;如果只是死循环生成对象,这些对象只会在堆中,没有GCRoots指向他们,他们是可以被回收的
 * <p>
 * -Xms10M -Xmx10M -XX:MaxDirectMemorySize=1M 改直接内存,效果不明显啊;都是要执行15万次才会出现
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 *
 * @author liuyanling
 * @date 2020-12-27 18:38
 */
public class GCOverHeadDemo {

    public static void main(String[] args) {

        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true) {
                //注意不加intern,容易先出现java heap space异常;
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("********" + i);
            e.printStackTrace();
            throw e;
        }

    }
}
