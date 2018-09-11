package com.serverless.demo.io;

public class CustomEventOutput {
    private Integer value;
    public CustomEventOutput(int value) {
        setValue(value);
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}