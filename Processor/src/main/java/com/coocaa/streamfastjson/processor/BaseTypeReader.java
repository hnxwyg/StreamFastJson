package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.CLASS_SUFFIX;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.PARSE_METHOD;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;

public class BaseTypeReader{

    public static CodeBlock getReaderCode(Element element,String name){
        return getReaderCode(element,null,name);
    }

    public static CodeBlock getReaderCode(Element element,TypeKind typeKind,String name) {
        CodeBlock.Builder builder = CodeBlock.builder();
        TypeKind typpKind = typeKind;
        if (typeKind == null){
            typpKind = ElementUtils.getTypeKind(element);
        }
        switch (typpKind){
            case INT:
                builder.addStatement("int " + name + " = " + READER + ".readInteger()");
                return builder.build();
            case BYTE:
                builder.addStatement("byte " + name + " = " + READER + ".readInteger().byteValue()");
                return builder.build();
            case SHORT:
                builder.addStatement("short " + name + " = " + READER + ".readInteger().shortValue()");
                return builder.build();
            case CHAR:
                builder.addStatement("char " + name + " = (char)" + READER + ".readInteger().intValue()");
                return builder.build();
            case LONG:
                builder.addStatement("long " + name + " = " + READER + ".readLong()");
                return builder.build();
            case FLOAT:
            case DOUBLE:
            case BOOLEAN:
                String type = "";
                switch (typpKind){
                    case FLOAT:
                        type = "Float";
                        break;
                    case DOUBLE:
                        type = "Double";
                        break;
                    case BOOLEAN:
                        type = "Boolean";
                        break;
                }
                builder.addStatement("String s = " + READER + ".readString()");
                builder.addStatement(type +" " + name + " = null");
                builder.beginControlFlow("try")
                        .addStatement(name + " = " + type + ".valueOf(s)")
                        .nextControlFlow("catch ($T e)", Exception.class)
                        .addStatement("e.printStackTrace()")
                        .endControlFlow();
                return builder.build();
            case DECLARED:
                String typeName = element.asType().toString();
                if (typeName.equals("java.lang.String")){
                     builder.addStatement("String " + name + " = " + READER + ".readString()");
                    return builder.build();
                }else{
                    String clazzName = element.getSimpleName().toString();
                    builder.addStatement("$T " + name + " = " +
                            clazzName + CLASS_SUFFIX + "." + PARSE_METHOD + "(reader)", ClassName.get(element.asType()));
                    return builder.build();
                }
        }
        return null;
    }
}
