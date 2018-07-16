package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.HashMap;
import java.util.HashSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.PARSE_METHOD;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.getListGeneric;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.getMapGeneric;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.mElementUtils;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.note;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class MapProcessor implements ITypeProcessor{
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        ElementKind kind = typeUtils.asElement(element.asType()).getKind();
        if (kind == ElementKind.INTERFACE){
            builder.addStatement(OBJECT + "." + name + " = new $T()", HashMap.class);
        }else{
            builder.addStatement(OBJECT + "." + name + " = new $T()", ClassName.get(element.asType()));
        }
        builder.addStatement(READER +".readObject(" + OBJECT + "." + name + ")");
        return builder.build();
    }
}
