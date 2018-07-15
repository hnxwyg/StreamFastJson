package com.coocaa.streamfastjson.processor;

public class LongProcessor extends BaseTypeProcessor {
    @Override
    public String getReadMethodName() {
        return "readLong";
    }
}
