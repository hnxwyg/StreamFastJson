package com.coocaa.streamfastjson.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
                }else if(isSubOfInterface(element, List.class)){
                    return new ListProcessor();
                }else if(isSubOfInterface(element,Set.class)){
                    return new SetProcessor();
                }else if(isSubOfInterface(element,Map.class)){
                    return new MapProcessor();
                }
                return new ObjectProcessor();
            default:
                return null;
        }
    }


}
