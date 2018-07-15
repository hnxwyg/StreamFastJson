package com.coocaa.streamfastjson.api;

public interface IJSONParser {
    public <T> T parseObject(String json,Class<T> clazz);
    public String toJSONString(Object o);
}
