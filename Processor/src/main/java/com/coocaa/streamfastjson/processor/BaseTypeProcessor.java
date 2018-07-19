package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;

public class BaseTypeProcessor implements ITypeProcessor {
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add(BaseTypeReader.getReaderCode(element,"object1"));
        builder.addStatement(OBJECT + "." + name + " = object1");
        return builder.build();
    }
}
