package com.lyl.concurrent.distruptor;

public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent getEvent() {
        return new StringEvent();
    }
}