package com.lyl.myself.distruptor;

public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent getEvent() {
        return new StringEvent();
    }
}