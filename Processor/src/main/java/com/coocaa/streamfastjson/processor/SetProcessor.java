package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.HashSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.PARSE_METHOD;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.getListGeneric;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.mElementUtils;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.note;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class SetProcessor implements ITypeProcessor{
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(READER + ".startArray()");
        String genericName = getListGeneric((DeclaredType) element.asType());
        builder.addStatement("$T set = null", ClassName.get(element.asType()));
        builder.beginControlFlow("while(" + READER + ".hasNext())");
        builder.beginControlFlow("if(set == null)");
        ElementKind kind = typeUtils.asElement(element.asType()).getKind();
        note("the kind " + kind);
        if (kind == ElementKind.INTERFACE){
            builder.addStatement("set = new $T()", HashSet.class);
        }else{
            builder.addStatement("set = new $T()", ClassName.get(element.asType()));
        }
        builder.endControlFlow();
        TypeElement ele = mElementUtils.getTypeElement(genericName);
        CodeBlock block = BaseTypeReader.getReaderCode(typeUtils.asElement(ele.asType()));
        if (block != null){
            builder.add(block);
            builder.addStatement("set.add(temp)");
        }else{
            builder.addStatement("$T temp = $T." + PARSE_METHOD + "(" + READER + ")",
                    ClassName.get(ele),ClassName.get(ele));
            builder.addStatement("set.add(temp)");
        }
        builder.endControlFlow();
        builder.addStatement(OBJECT + "." + name + " = set");
        builder.addStatement(READER + ".endArray()");
        return builder.build();
    }
}
