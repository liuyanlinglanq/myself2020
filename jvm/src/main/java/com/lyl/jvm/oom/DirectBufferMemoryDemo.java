package com.lyl.jvm.oom;

/**
 * 直接内存
 *
 * @author liuyanling
 * @date 2021年02月07日11:10:00
 */
public class DirectBufferMemoryDemo {

	public static void main(String[] args) {
		// 配置的Max Direct Memory:1820MB,差不多就是2G,对应我8G的内存的1/4
		System.out.println("配置的Max Direct Memory:" + sun.misc.VM.maxDirectMemory() / 1024 / 1024 + "MB");
	}
}