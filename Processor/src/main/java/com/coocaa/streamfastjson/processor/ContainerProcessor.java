package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.type.DeclaredType;

public abstract class ContainerProcessor {
    protected DeclaredType declaredType = null;
    protected String objectName = "";
    protected CodeBlock.Builder codeBlockBuilder = null;
    public ContainerProcessor(DeclaredType type, String objecName){
        this.declaredType = type;
        this.objectName = objecName;
        codeBlockBuilder = CodeBlock.builder();
    }

    public CodeBlock getCodeBlock(){
        return codeBlockBuilder.build();
    }

    public abstract void buildBeforeCode();
    public abstract void addMiddleCode(CodeBlock codeBlock,String name);
    public abstract void buildAfterCode();
}
