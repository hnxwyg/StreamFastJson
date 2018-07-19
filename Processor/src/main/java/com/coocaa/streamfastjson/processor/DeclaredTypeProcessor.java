package com.coocaa.streamfastjson.processor;

import com.squareup.javapoet.CodeBlock;

import java.util.Stack;

import javax.lang.model.element.Element;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.OBJECT;

public class DeclaredTypeProcessor implements ITypeProcessor{
    private Stack<String> nameStack = new Stack<>();
    @Override
    public CodeBlock process(Element element) {
        ParsedElement parsedElement = ElementUtils.parseElement(element);
        CodeBlock codeBlock = getCodeBlock(parsedElement,1);
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add(codeBlock);
        builder.addStatement(OBJECT + "." + element.getSimpleName().toString()
                + " = object1");
        return builder.build();
    }


    private CodeBlock getCodeBlock(ParsedElement parsedElement,int level){
        ContainerProcessor arrayProcessor = null;
        String name = "object" + level;
        nameStack.push(name);
        level++;
        switch (parsedElement.kind){
            case BEAN:
                CodeBlock codeBlock =  BaseTypeReader.getReaderCode(parsedElement.declaredType.asElement(),name);
                return codeBlock;
            case MAP:
                arrayProcessor = new MapProcessor(parsedElement.declaredType,name);
                arrayProcessor.buildBeforeCode();
                arrayProcessor.addMiddleCode(getCodeBlock(parsedElement.getSubElement().get(0),level),nameStack.pop());
                level++;
                arrayProcessor.addMiddleCode(getCodeBlock(parsedElement.getSubElement().get(1),level),nameStack.pop());
                arrayProcessor.buildAfterCode();
                break;
            case LIST:
                arrayProcessor = new ListProcessor(parsedElement.declaredType,name);
                arrayProcessor.buildBeforeCode();
                arrayProcessor.addMiddleCode(getCodeBlock(parsedElement.getSubElement().get(0),level),nameStack.pop());
                arrayProcessor.buildAfterCode();
                break;
            case SET:
                arrayProcessor = new SetProcessor(parsedElement.declaredType,name);
                arrayProcessor.buildBeforeCode();
                arrayProcessor.addMiddleCode(getCodeBlock(parsedElement.getSubElement().get(0),level),nameStack.pop());
                arrayProcessor.buildAfterCode();
                break;
        }
        return arrayProcessor.getCodeBlock();
    }

}
