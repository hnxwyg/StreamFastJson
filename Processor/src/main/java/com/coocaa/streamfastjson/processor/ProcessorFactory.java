package com.coocaa.streamfastjson.processor;

public class ProcessorFactory {


    public static ITypeProcessor getTypeProcessor(String type){
        switch (type){
            case "int":return new IntProcessor();
            case "java.lang.String":return new StringProcessor();
            case "long":return new LongProcessor();
            case "float":return new FloatProcessor();
            case "byte":return new ByteProcessor();
            case "short":return new ShortProcessor();
            case "char":return new CharProcessor();
            default:
                return new ObjectProcessor();
        }
    }
}
