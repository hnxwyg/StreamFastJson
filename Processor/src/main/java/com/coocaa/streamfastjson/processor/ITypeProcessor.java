package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;

public interface ITypeProcessor {
    public CodeBlock process(Element element);
}
