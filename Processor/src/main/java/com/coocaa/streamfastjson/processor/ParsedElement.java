package com.coocaa.streamfastjson.processor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.DeclaredType;

public class ParsedElement{
    private List<ParsedElement> subElement = null;
    public DeclaredType declaredType = null;
    public Kind kind = Kind.LIST;
    public enum Kind{
        LIST,
        MAP,
        SET,
        BEAN
    }

    public List<ParsedElement> getSubElement() {
        return subElement;
    }

    public int subElementCount(){
        return subElement == null ? 0 : subElement.size();
    }

    public void addSubElement(ParsedElement element){
        if (subElement == null)
            subElement = new ArrayList<>();
        subElement.add(element);
    }
}
