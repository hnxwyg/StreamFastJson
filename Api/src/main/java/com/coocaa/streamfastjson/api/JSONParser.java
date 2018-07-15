package com.coocaa.streamfastjson.api;

import com.alibaba.fastjson.JSONObject;

public class JSONParser implements IJSONParser {

    private static JSONParser parser = new JSONParser();
    public IJSONParser getInstance(){
        return parser;
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        T o = parseByStream(json,clazz);
        if (o == null){
            o = JSONObject.parseObject(json,clazz);
        }
        return o;
    }

    @Override
    public String toJSONString(Object o) {
        return JSONObject.toJSONString(o);
    }


    private <T> T parseByStream(String json,Class<T> clazz){
        return null;
    }
}
