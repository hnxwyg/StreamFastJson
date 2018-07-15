package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;

public class ListProcessor implements ITypeProcessor {

    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(READER + ".startArray()");
        builder.addStatement("List<$T> list = null");
        builder.beginControlFlow("while(" + READER + ".hasNext())");
        builder.beginControlFlow("if(list == null)");
        builder.addStatement("list = new ArrayList<>()");
        builder.endControlFlow();
        builder.addStatement("list.add()");
        builder.endControlFlow();
        builder.addStatement(OBJECT + "." + name + " = list");
        builder.addStatement(READER + ".endArray()");
        return builder.build();
    }
}
