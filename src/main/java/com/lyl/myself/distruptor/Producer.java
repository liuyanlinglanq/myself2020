package com.lyl.myself.distruptor;

public interface Producer<E, V> {
    int getProducerIndex(); // 获取当前生产者投放的元素的位置
 
    void produce(V value); // 生产元素
 
    void setDistruptor(AnotherDistruptor<E, V> AnotherDistruptor);
}
