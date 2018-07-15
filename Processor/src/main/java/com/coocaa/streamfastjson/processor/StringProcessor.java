package com.coocaa.streamfastjson.processor;


public class StringProcessor extends BaseTypeProcessor {

    @Override
    public String getReadMethodName() {
        return "readString";
    }
}
