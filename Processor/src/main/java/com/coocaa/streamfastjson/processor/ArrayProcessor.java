package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.mElementUtils;

public class ArrayProcessor implements ITypeProcessor{
    public String objectName = "object1";
    @Override
    public CodeBlock process(Element element) {
        String fieldName = element.getSimpleName().toString();
        ArrayType type = (ArrayType) element.asType();
        TypeMirror typeMirror = type.getComponentType();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(READER + ".startArray()");
        String name = "list";
        TypeElement typeElement = ElementUtils.boxClass(typeMirror.getKind());
        TypeMirror boxTypeMirror = null;
        if (typeElement != null)
            boxTypeMirror = typeElement.asType();
        builder.addStatement("$T<$T> " + name + " = null",
                ClassName.get(List.class),
                ClassName.get(boxTypeMirror == null? typeMirror:boxTypeMirror));
        builder.beginControlFlow("while(" + READER + ".hasNext())");
        builder.beginControlFlow("if(" + name + " == null)");
        builder.addStatement(name + " = new $T()",ClassName.get(ArrayList.class));
        builder.endControlFlow();
        Element e = mElementUtils.getTypeElement(typeMirror.toString());
        CodeBlock block = null;
        if (ElementUtils.isBoxClass(typeMirror)){
            block = BaseTypeReader.getReaderCode(e,"item");
        }else{
            block = BaseTypeReader.getReaderCode(e,typeMirror.getKind(),"item");
        }
        builder.add(block);
        builder.addStatement(name + ".add(item)");
        builder.endControlFlow();
        builder.addStatement(READER + ".endArray()");
        builder.beginControlFlow("if(list != null)");
        builder.addStatement("$T[] " + objectName + " = "
                        + "($T[])" + name + ".toArray()",
                ClassName.get(boxTypeMirror == null? typeMirror:boxTypeMirror),
                ClassName.get(boxTypeMirror == null? typeMirror:boxTypeMirror));
        if (boxTypeMirror != null){
            builder.addStatement("int length = list.size()");
            builder.addStatement("$T[] temp = new $T[length]",ClassName.get(typeMirror),ClassName.get(typeMirror));
            builder.beginControlFlow("for(int i = 0;i < length;i++)");
            builder.addStatement("temp[i] = list.get(i)");
            builder.endControlFlow();
            builder.addStatement(OBJECT + "." + fieldName + " = temp");
        }else{
            builder.addStatement(OBJECT + "." + fieldName + " = " + objectName);
        }
        builder.endControlFlow();
        return builder.build();
    }
}
