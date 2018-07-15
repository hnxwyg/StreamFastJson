package com.coocaa.streamfastjson.processor;


import com.alibaba.fastjson.StreamReader;
import com.coocaa.streamfastjson.annotation.StreamFastJson;
import com.coocaa.streamfastjson.api.IStreamFastJson;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by luwei on 18-6-7.
 */
@AutoService(Processor.class)
public class StreamFastJsonProcessor extends AbstractProcessor {

    private static Filer mFiler;
    private static Messager mMessager;
    public static Types typeUtils = null;
    public static Elements mElementUtils;
    private RoundEnvironment mRoundEnv = null;
    private Map<String, List<Element>> elementMap = new HashMap<>();
    private HashMap<String,Element> fieldNameMaps = new HashMap<>();
    private Set<Element> processedElement = new HashSet<>();
    private static boolean hasProcess = false;
    private static final String CLASS_SUFFIX = "_JSON";
    public static final String PARSE_METHOD = "parseObject";
    public static final String JSON_METHOD = "toJSONString";
    public static final String OBJECT = "object";
    public static final String READER = "reader";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(StreamFastJson.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        note("begin process");
        long start = System.currentTimeMillis();
        if (hasProcess)
            return true;
        hasProcess = true;
        this.mRoundEnv = roundEnvironment;
        Set<? extends Element> streamFastJsonElements = roundEnvironment.getElementsAnnotatedWith(StreamFastJson.class);
        for (Element streamFastJsonElement : streamFastJsonElements) {
            if (!(streamFastJsonElement instanceof TypeElement))
                continue;
            String pkg = streamFastJsonElement.getEnclosingElement().asType().toString();
            String name = streamFastJsonElement.getSimpleName().toString();
            TypeSpec.Builder clazzTypeBuilder = TypeSpec.classBuilder(name + CLASS_SUFFIX)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            MethodSpec parseMethod = generateParseMethod(streamFastJsonElement);
            MethodSpec jsonMethod = generateJsonMethod(streamFastJsonElement);
            clazzTypeBuilder.addMethod(parseMethod);
            clazzTypeBuilder.addMethod(jsonMethod);
            JavaFile javaFile = JavaFile.builder(pkg, clazzTypeBuilder.build())
                    .build();
            try {
                javaFile.toJavaFileObject().delete();
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        note("stream fastjson process annotation cost " + (System.currentTimeMillis() - start) + "ms");
        return false;
    }
    
    
    
    private MethodSpec generateParseMethod(Element element){
        MethodSpec.Builder parseObject = MethodSpec.methodBuilder(PARSE_METHOD)
                .addModifiers(Modifier.STATIC,Modifier.PUBLIC)
                .returns(ClassName.get((TypeElement) element))
                .addParameter(StreamReader.class, "reader");
        List<Element> elementList = (List<Element>) element.getEnclosedElements();
        parseObject.addStatement("String key = \"\"");
        parseObject.addStatement("reader.startObject();");
        parseObject.addStatement("$T object = null;",ClassName.get((TypeElement) element));
        parseObject.beginControlFlow("while(reader.hasNext())");
        parseObject.beginControlFlow("if (object == null)");
        parseObject.addStatement("object = new TestData()");
        parseObject.endControlFlow();
        parseObject.addStatement("key = reader.readString()");
        boolean firstElement = true;
        for (Element ele : elementList) {
//            if (!(ele instanceof VariableElement))
//                continue;
//            ITypeProcessor processor = ProcessorFactory.getTypeProcessor(ele.asType().toString());
//            if (processor != null){
//                if (firstElement){
//                    parseObject.beginControlFlow("if(\"$L\".equals(key))",ele.getSimpleName().toString());
//                    firstElement = false;
//                }else{
//                    parseObject.nextControlFlow("else if(\"$L\".equals(key))",ele.getSimpleName().toString());
//                }
//                parseObject.addCode(processor.process(ele));
//            }
            note(ele.asType().toString());
            if (ele.asType().toString().contains("List")){
                note(typeUtils.capture(ele.asType()).toString());
                note(typeUtils.directSupertypes(ele.asType()).toString());
                note(typeUtils.erasure(ele.asType()).toString());
                note(typeUtils.getArrayType(ele.asType()).getComponentType().toString());
                note(mElementUtils.getBinaryName((TypeElement) typeUtils.asElement(ele.asType())).toString());
                note(ClassName.get(ele.asType()).getClass().);

            }
        }
        if (!firstElement) {
            parseObject.nextControlFlow("else");
            parseObject.addStatement("reader.readObject()");
            parseObject.endControlFlow();
        }
        parseObject.endControlFlow();
        parseObject.addStatement("reader.endObject()");
        parseObject.addStatement("return object");
        return parseObject.build();
    }
    
    private MethodSpec generateJsonMethod(Element element){
        MethodSpec.Builder toJsonString = MethodSpec.methodBuilder(JSON_METHOD)
                .addModifiers(Modifier.STATIC,Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(Object.class, "reader")
                .addStatement("return \"\"");
        return toJsonString.build();
        
    }

    private void generateMethod(ClassName target, TypeSpec.Builder clazzTypeBuilder, List<String> methods, Element element) {
        if (processedElement.contains(element))
            return;
        processedElement.add(element);
        //因为BindView只作用于filed，所以这里可直接进行强转
        VariableElement bindViewElement = (VariableElement) element;
        //3.获取注解的成员变量名
        String fieldName = bindViewElement.getSimpleName().toString();
        //3.获取注解的成员变量类型
        String classType = bindViewElement.asType().toString();
        //4.获取注解元数据
        String methodName = "add" + fieldName;


    }

    public static void note(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public static void note(String format, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(format, args));
    }

    public static boolean isEmpty(String s){
        if (s == null || s.equals(""))
            return true;
        return false;
    }

}