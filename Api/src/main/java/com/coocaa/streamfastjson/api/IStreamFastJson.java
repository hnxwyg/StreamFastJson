package com.coocaa.streamfastjson.api;

import com.alibaba.fastjson.StreamReader;

public interface IStreamFastJson {
    public Object parseObject(StreamReader reader);
    public String toJSONString(Object o);
}
