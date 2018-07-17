package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.PARSE_METHOD;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.getListGeneric;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.isSubOfInterface;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.note;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class ListProcessor implements ITypeProcessor {

    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(READER + ".startArray()");
        TypeMirror genericType = getListGeneric((DeclaredType) element.asType());
        builder.addStatement("$T list = null",ClassName.get(element.asType()));
        builder.beginControlFlow("while(" + READER + ".hasNext())");
        builder.beginControlFlow("if(list == null)");
        ElementKind kind = typeUtils.asElement(element.asType()).getKind();
        if (kind == ElementKind.INTERFACE){
            builder.addStatement("list = new $T()", ArrayList.class);
        }else{
            builder.addStatement("list = new $T()", ClassName.get(element.asType()));
        }
        builder.endControlFlow();
        TypeElement ele = (TypeElement) typeUtils.asElement(genericType);
        note("the ele is " + genericType.toString());
        CodeBlock block = null;
        if (ele != null){
            block = BaseTypeReader.getReaderCode(typeUtils.asElement(ele.asType()));
        }
        if (block != null){
            builder.add(block);
            builder.addStatement("list.add(temp)");
        }else if (isSubOfInterface(typeUtils.asElement(ele.asType()), Collection.class) ||
                isSubOfInterface(typeUtils.asElement(ele.asType()),Map.class)){
            builder.addStatement("list.add(reader.readObject($T.class))",ClassName.get(genericType));
        }else{
            builder.addStatement("$T temp = $T." + PARSE_METHOD + "(" + READER + ")",
                    ClassName.get(ele),ClassName.get(ele));
            builder.addStatement("list.add(temp)");
        }
        builder.endControlFlow();
        builder.addStatement(OBJECT + "." + name + " = list");
        builder.addStatement(READER + ".endArray()");
        return builder.build();
    }
}
