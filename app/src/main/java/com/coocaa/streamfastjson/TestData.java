package com.coocaa.streamfastjson;

import com.alibaba.fastjson.StreamReader;
import com.coocaa.streamfastjson.annotation.StreamFastJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@StreamFastJson
public class TestData {
    public int intValue= 100;
    public String stringValue = "aaa";
    public long longValue = 100l;
    public float floatValue = 1.1f;
    public byte byteValue = 'c';
    public short shortValue = 20;
    public char charValue = 'a';
    public double doubleValue = 11;
    public TestData testData = null;
    public List<TestData> listData = null;
    public List<String> listString = null;
    public Map<TestData,TestData> mapData = null;
    public Set<TestData> setData = null;
    public LinkedList<TestData> linkedList = null;
    public SubTestData subTestData = null;


    public static TestData parseObject(StreamReader reader){
        String key = "";
        reader.startObject();
        TestData object = null;
        while (reader.hasNext()){
            if (object == null)
                object = new TestData();
            key = reader.readString();
            if ("intValue".equals(key)){
                object.intValue = reader.readInteger();
            }else if("stringValue".equals(key)){
                object.stringValue = reader.readString();
            }else if("longValue".equals(key)){
                object.longValue = reader.readLong();
            }else if("floatValue".equals(key)){
                String s = reader.readString();
                try {
                    object.floatValue = Float.valueOf(s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else if("byteValue".equals(key)){
                object.byteValue = reader.readInteger().byteValue();
            }else if("shoreValue".equals(key)){
                object.shortValue = reader.readInteger().shortValue();
            }else if("charValue".equals(key)){
                object.charValue = (char)reader.readInteger().intValue();
            }else if("doubleValue".equals(key)){
                object.doubleValue = Double.valueOf(reader.readString());
            }else if("testData".equals(key)){
                object.testData = TestData.parseObject(reader);
            }else if("listData".equals(key)){
                reader.startArray();
                List<TestData> list = new ArrayList<>();
                while (reader.hasNext()){
                    if (list == null)
                        list = new ArrayList<>();
                    list.add(TestData.parseObject(reader));
                }
                reader.endArray();
            }else if("mapData".equals(key)){
                object.mapData = new HashMap<>();
                reader.readObject(object.mapData);
            }else if("setData".equals(key)){
                reader.startArray();
                List<TestData> list = new ArrayList<>();
                while (reader.hasNext()){
                    if (list == null)
                        list = new ArrayList<>();
                    list.add(TestData.parseObject(reader));
                }
                reader.endArray();
            }else{
                Object o = reader.readObject();
            }
        }
        reader.endObject();
        return object;
    }

    @StreamFastJson
    public static class SubTestData{
        public String title = "";
    }
}
