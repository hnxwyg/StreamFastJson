package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;

public class CastTypeProcessor implements ITypeProcessor{
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        TypeKind kind = element.asType().getKind();
        String type = "";
        switch (kind){
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
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement("String s = " + READER + ".readString()");
        builder.beginControlFlow("try")
                .addStatement(OBJECT + "." + name + " = " + type + ".valueOf(s)")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("e.printStackTrace()")
                .endControlFlow();
        return builder.build();
    }
}
