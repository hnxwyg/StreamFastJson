package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.getListGeneric;

public class ListProcessor implements ITypeProcessor {

    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(READER + ".startArray()");
        String genericName = getListGeneric((DeclaredType) element.asType());
        builder.addStatement("$T list = null",ClassName.get(element.asType()));
        builder.beginControlFlow("while(" + READER + ".hasNext())");
        builder.beginControlFlow("if(list == null)");
        ElementKind kind = element.getKind();
        if (kind == ElementKind.INTERFACE){
            builder.addStatement("list = new ArrayList<>()");
        }else{
            builder.addStatement("list = new $T<>()", ClassName.get(element.asType()));
        }
        builder.endControlFlow();
        builder.addStatement("list.add()");
        builder.endControlFlow();
        builder.addStatement(OBJECT + "." + name + " = list");
        builder.addStatement(READER + ".endArray()");
        return builder.build();
    }
}
