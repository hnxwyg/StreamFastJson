package com.coocaa.streamfastjson.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.isSubOfInterface;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.mElementUtils;
import static com.coocaa.streamfastjson.processor.StreamFastJsonProcessor.typeUtils;

public class ElementUtils {

    public static ParsedElement parseElement(Element ele) {
        ParsedElement parsedElement = new ParsedElement();
        parsedElement.declaredType = (DeclaredType) ele.asType();
        parsedElement.kind = getKind(ele);
        parseType((DeclaredType) ele.asType(), parsedElement);
        return parsedElement;
    }


    private static void parseType(DeclaredType type, ParsedElement parsedElement) {
        List<TypeMirror> typeMirrors = (List<TypeMirror>) type.getTypeArguments();
        if (typeMirrors.size() > 0) {
            for (TypeMirror mirror : typeMirrors) {
                if (mirror instanceof DeclaredType) {
                    ParsedElement subElement = new ParsedElement();
                    Element element = ((DeclaredType) mirror).asElement();
                    subElement.declaredType = (DeclaredType) mirror;
                    subElement.kind = getKind(element);
                    parseType((DeclaredType) mirror, subElement);
                    parsedElement.addSubElement(subElement);
                }
            }
        }
    }


    public static ParsedElement.Kind getKind(Element element) {
        if (isSubOfInterface(element, List.class))
            return ParsedElement.Kind.LIST;
        if (isSubOfInterface(element, Map.class))
            return ParsedElement.Kind.MAP;
        if (isSubOfInterface(element, Set.class))
            return ParsedElement.Kind.SET;
        return ParsedElement.Kind.BEAN;
    }


    public static TypeKind getTypeKind(Element element) {
        TypeKind typpKind = element.asType().getKind();
        try {
            PrimitiveType primitiveType = typeUtils.unboxedType(element.asType());
            if (primitiveType != null) {
                typpKind = primitiveType.getKind();
            }
        } catch (Exception e) {
        }
        return typpKind;
    }

    public static boolean isBoxClass(TypeMirror typeMirror) {
        try {
            PrimitiveType primitiveType = typeUtils.unboxedType(typeMirror);
            if (primitiveType != null)
                return true;
        } catch (Exception e) {
        }
        return false;
    }


    public static TypeElement boxClass(TypeKind kind) {
        switch (kind) {
            case INT:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Integer.class.getName()).asType()));
            case LONG:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Long.class.getName()).asType()));
            case FLOAT:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Float.class.getName()).asType()));
            case BYTE:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Byte.class.getName()).asType()));
            case SHORT:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Short.class.getName()).asType()));
            case CHAR:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Character.class.getName()).asType()));
            case DOUBLE:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Double.class.getName()).asType()));
            case BOOLEAN:
                return typeUtils.boxedClass(typeUtils.unboxedType(mElementUtils.getTypeElement(Boolean.class.getName()).asType()));
            default:
                return null;
        }
    }

}
