package com.coocaa.streamfastjson.processor;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.isSubOfInterface;

public class ProcessorFactory {


    public static ITypeProcessor getTypeProcessor(Element element){
        TypeKind typpKind = element.asType().getKind();
        switch (typpKind){
            case INT:
            case LONG:
            case FLOAT:
            case BYTE:
            case SHORT:
            case CHAR:
            case DOUBLE:
            case BOOLEAN:
                return new BaseTypeProcessor();
            case ARRAY:return null;
            case DECLARED:
                String name = element.asType().toString();
                if (name.equals("java.lang.String")){
                    return new BaseTypeProcessor();
                }else if(isSubOfInterface(element, "java.util.List<E>")){
                    return new ListProcessor();
                }
                return new ObjectProcessor();
            default:
                return null;
        }
    }


}
