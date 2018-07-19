package com.coocaa.streamfastjson.processor;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class ProcessorFactory {


    public static ITypeProcessor getTypeProcessor(Element element){
        TypeKind typpKind = ElementUtils.getTypeKind(element);
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
            case ARRAY:
                return new ArrayProcessor();
            case DECLARED:
                String name = element.asType().toString();
                if (name.equals("java.lang.String")){
                    return new BaseTypeProcessor();
                }else {
                    return new DeclaredTypeProcessor();
                }
            default:
                return null;
        }
    }


}
