package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.HashSet;

import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class SetProcessor extends ContainerProcessor{
    public SetProcessor(DeclaredType type, String objecName) {
        super(type, objecName);
    }

    @Override
    public void buildBeforeCode() {
        codeBlockBuilder.addStatement(READER + ".startArray()");
        codeBlockBuilder.addStatement("$T " + objectName + " = null", ClassName.get(declaredType));
        codeBlockBuilder.beginControlFlow("while(" + READER + ".hasNext())");
        codeBlockBuilder.beginControlFlow("if(" + objectName + " == null)");
        ElementKind kind = typeUtils.asElement(declaredType).getKind();
        if (kind == ElementKind.INTERFACE){
            codeBlockBuilder.addStatement(objectName + " = new $T()", HashSet.class);
        }else{
            codeBlockBuilder.addStatement(objectName + " = new $T()", ClassName.get(declaredType));
        }
        codeBlockBuilder.endControlFlow();
    }

    @Override
    public void addMiddleCode(CodeBlock codeBlock, String name) {
        codeBlockBuilder.add(codeBlock);
        codeBlockBuilder.beginControlFlow("if(" + name + " != null)");
        codeBlockBuilder.addStatement(objectName + ".add(" + name + ")");
        codeBlockBuilder.endControlFlow();
    }

    @Override
    public void buildAfterCode() {
        codeBlockBuilder.endControlFlow();
        codeBlockBuilder.addStatement(READER + ".endArray()");
    }
}
