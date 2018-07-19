package com.coocaa.streamfastjson;

import com.alibaba.fastjson.JSONReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public String title = "aaaa";
    public List<List<SubData>> dataList = new ArrayList<>();
    public Map<String,List<SubData>> mapData = new HashMap<>();

    public Data(){
        dataList.add(new ArrayList<SubData>());
        dataList.get(0).add(new SubData());
        mapData.put(title,dataList.get(0));

    }

    public static Data parseJSON(JSONReader reader){
        Data data = new Data();
        reader.startObject();
        while (reader.hasNext()){
            String key = reader.readString();
            if ("title".equals(key)){
                data.title = reader.readString();
            }else if("dataList".equals(key)){
                reader.startArray();
                List<List<SubData>> listt = new ArrayList<>();
                while (reader.hasNext()){
                    List<SubData> list = new ArrayList<>();
                    reader.startArray();
                    while (reader.hasNext()){
                        list.add(reader.readObject(SubData.class));
                    }
                    reader.endArray();
                    listt.add(list);
                }
                reader.endArray();
                data.dataList = listt;
            }else if("mapData".equals(key)) {
                Map<String,List<SubData>> map = new HashMap<>();
                reader.startArray();
                while (reader.hasNext()){
                    reader.startObject();
                    key = reader.readString();
                    reader.startArray();
                    List<SubData> list = new ArrayList<>();
                    while (reader.hasNext()){
                        list.add(reader.readObject(SubData.class));
                        reader.readString();
                    }
                    reader.endArray();
                    map.put(key,list);
                }
                reader.endArray();
                data.mapData = map;
            } else{
                reader.readObject();
            }
        }
        reader.endObject();
        return data;
    }

    public static class SubData{
        public String sub = "bbbb";
    }

}
