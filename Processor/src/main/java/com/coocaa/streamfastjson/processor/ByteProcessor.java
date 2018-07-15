package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;

public class ByteProcessor implements ITypeProcessor {
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(OBJECT + "." + name + " = " + READER + ".readInteger().byteValue()");
        return builder.build();
    }
}