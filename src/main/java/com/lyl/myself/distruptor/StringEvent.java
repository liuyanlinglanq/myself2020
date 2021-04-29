package com.lyl.myself.distruptor;

public class StringEvent implements Event<String> {
    private String value;

    @Override
    public String getData() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}