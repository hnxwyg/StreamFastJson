package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.CLASS_SUFFIX;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.PARSE_METHOD;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.READER;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.mElementUtils;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.note;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class ObjectProcessor implements ITypeProcessor {
    @Override
    public CodeBlock process(Element element) {
        String name = element.getSimpleName().toString();
        Element typeElement = typeUtils.asElement(element.asType());
        String pkg = mElementUtils.getPackageOf(element).toString();
        String clazz = typeElement.getSimpleName().toString();
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(OBJECT + "." + name + " = $T." + PARSE_METHOD + "(" + READER + ")",
                ClassName.get(pkg,clazz + CLASS_SUFFIX));
        return builder.build();
    }
}
