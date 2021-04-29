package com.lyl.myself.distruptor;

public class StringConsumer implements Consumer<StringEvent> {
 
    @Override
    public void consume(StringEvent event) {
        System.out.println(event.getData());
    }
}