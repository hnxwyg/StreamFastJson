package com.coocaa.streamfastjson.processor;

public class IntProcessor extends BaseTypeProcessor {
    @Override
    public String getReadMethodName() {
        return "readInteger";
    }
}
