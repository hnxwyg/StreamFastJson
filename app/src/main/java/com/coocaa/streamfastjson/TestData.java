package com.coocaa.streamfastjson;

import com.coocaa.streamfastjson.annotation.StreamFastJson;

import java.util.List;
import java.util.Map;

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
    public boolean booleanValue = false;
//
    public Integer intValue1= 100;
    public Long longValue1 = 100l;
    public Float floatValue1 = 1.1f;
    public Byte byteValue1 = 'c';
    public Short shortValue1 = 20;
    public Character charValue1 = 'a';
    public Double doubleValue1 = Double.valueOf(11);
    public Boolean booleanValue1 = false;
//
//
//
    public String[] array1 = null;
    public Integer[] array2 = null;
    public Long[] array3 = null;
    public Float[] array4 = null;
    public Byte[] array5 = null;
    public Short[] array6 = null;
    public Character[] array7 = null;
    public Double[] array8 = null;
    public Boolean[] array9 = null;
//
    public int[] array10 = null;
    public long[] array11 = null;
    public float[] array12 = null;
    public byte[] array13 = null;
    public short[] array14 = null;
    public char[] array15 = null;
    public double[] array16 = null;
    public boolean[] array17 = null;
//
//
//    public TestData testData = null;
//
//
//
    public List<String> list1 = null;
    public List<Integer> list2 = null;
    public List<Float> list3 = null;
    public List<Byte> list4= null;
    public List<Character> list5 = null;
    public List<Double> list6 = null;
    public List<Boolean> list7 = null;
    public List<Long> list8 = null;
    public List<TestData> list9 = null;
    public List<List<String>> list10 = null;
    public List<List<List<SubTestData>>> list11 = null;
//    public List<int[]> list12 = null;
//
    public Map<String,String> map1 = null;
    public Map<String,TestData> map2 = null;
    public Map<String,List<String>> map3 = null;
    public Map<String,List<SubTestData>> map4 = null;
    public Map<Map<String,String>,List<SubTestData>> map5 = null;
//
//
//    public Map<TestData,TestData> mapData = null;
//    public Set<TestData> setData = null;
//    public LinkedList<TestData> linkedList = null;
//    public SubTestData subTestData = null;
//    public List<List<List<String>>> subList = null;
//    public Map<String,List<SubTestData>> mapList = null;
//    public Map<String,String> mapList1 = null;



//    public static TestData parseObject(StreamReader reader){
//        String key = "";
//        reader.startObject();
//        TestData object = null;
//        while (reader.hasNext()){
//            if (object == null)
//                object = new TestData();
//            key = reader.readString();
//            if ("intValue".equals(key)){
//                object.intValue = reader.readInteger();
//            }else if("stringValue".equals(key)){
//                object.stringValue = reader.readString();
//            }else if("longValue".equals(key)){
//                object.longValue = reader.readLong();
//            }else if("floatValue".equals(key)){
//                String s = reader.readString();
//                try {
//                    object.floatValue = Float.valueOf(s);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//            }else if("byteValue".equals(key)){
//                object.byteValue = reader.readInteger().byteValue();
//            }else if("shoreValue".equals(key)){
//                object.shortValue = reader.readInteger().shortValue();
//            }else if("charValue".equals(key)){
//                object.charValue = (char)reader.readInteger().intValue();
//            }else if("doubleValue".equals(key)){
//                object.doubleValue = Double.valueOf(reader.readString());
//            }else if("testData".equals(key)){
//                object.testData = TestData.parseObject(reader);
//            }else if("listData".equals(key)){
//                reader.startArray();
//                List<TestData> list = new ArrayList<>();
//                while (reader.hasNext()){
//                    if (list == null)
//                        list = new ArrayList<>();
//                    list.add(TestData.parseObject(reader));
//                }
//                reader.endArray();
//            }else if("mapData".equals(key)){
////                object.mapData = new HashMap<>();
////                reader.readObject(object.mapData);
//            }else if("setData".equals(key)){
//                reader.startArray();
//                List<TestData> list = new ArrayList<>();
//                while (reader.hasNext()){
//                    if (list == null)
//                        list = new ArrayList<>();
//                    list.add(TestData.parseObject(reader));
//                }
//                reader.endArray();
//            }else{
//                Object o = reader.readObject();
//            }
//        }
//        reader.endObject();
//        return object;
//    }

    @StreamFastJson
    public static class SubTestData{
        public String title = "";
    }
}
