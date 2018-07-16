package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;

public class BaseTypeReader{
    public static CodeBlock getReaderCode(Element element) {
        CodeBlock.Builder builder = CodeBlock.builder();
        TypeKind typpKind = element.asType().getKind();
        switch (typpKind){
            case INT:
                builder.addStatement("int temp = " + READER + ".readInteger()");
                break;
            case BYTE:
                builder.addStatement("byte temp = " + READER + ".readInteger().byteValue()");
                break;
            case SHORT:
                builder.addStatement("short temp = " + READER + ".readInteger().shortValue()");
                break;
            case CHAR:
                builder.addStatement("char temp = (char)" + READER + ".readInteger().intValue()");
                break;
            case LONG:
                builder.addStatement("long temp = " + READER + ".readLong()");
                break;
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
                builder.addStatement(type +" temp = null");
                builder.beginControlFlow("try")
                        .addStatement("temp = " + type + ".valueOf(s)")
                        .nextControlFlow("catch ($T e)", Exception.class)
                        .addStatement("e.printStackTrace()")
                        .endControlFlow();
                break;
            case DECLARED:
                String name = element.asType().toString();
                if (name.equals("java.lang.String")){
                     builder.addStatement("String temp = " + READER + ".readString()");
                }
                break;
        }
        return builder.build();
    }
}
