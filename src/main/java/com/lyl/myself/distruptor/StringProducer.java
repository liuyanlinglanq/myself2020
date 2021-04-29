package com.lyl.myself.distruptor;

public class StringProducer implements Producer<StringEvent, String> {
    private AnotherDistruptor<StringEvent, String> anotherDistruptor;
    private int producerIndex = 0;
    @Override
    public int getProducerIndex() {
        return producerIndex;
    }
 
    public void produce(String value) {
        int index = anotherDistruptor.getNextWriteIndex();
        StringEvent event = anotherDistruptor.getEvent(index);
        event.setValue(value);
        anotherDistruptor.publish(index);
        System.out.println(Thread.currentThread().getName() + "set state true: " + index);
        this.producerIndex = index;
    }
 
    @Override
    public void setDistruptor(AnotherDistruptor<StringEvent, String> anotherDistruptor) {
        this.anotherDistruptor = anotherDistruptor;
    }
}