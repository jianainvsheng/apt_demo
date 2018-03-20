package com.example.binness.annotationbean;

import com.example.binness.baseannotation.ListDataAnnotation;
import com.example.model.FieldData;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public abstract class FieldAnnotation<D extends FieldData,T extends Annotation> extends ListDataAnnotation<D ,T> {

    @Override
    public ElementKind getElementKind() {
        return ElementKind.FIELD;
    }

    @Override
    public D analysis(T t, Element element, Elements elementUtils) throws IllegalStateException {

        Set<Modifier> modifiers = element.getModifiers();
        boolean isPublic = false;
        Iterator<Modifier> iterator = modifiers.iterator();
        while (iterator.hasNext()){
            Modifier modifier = iterator.next();
            if(modifier == Modifier.PUBLIC){
                isPublic = true;
            }
        }
        if(!isPublic){
            throw new IllegalStateException(getLog("this is not a public field"));
        }
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = getPackageName(elementUtils,typeElement);
        String className = typeElement.getSimpleName().toString();
        String fieldName = element.getSimpleName().toString();

        //处理匿名内部类的包名地址
        if(!(packageName + "."+ className).equals(typeElement.toString()) &&
                packageName.length() - 1 <  typeElement.toString().length()){

            className = (typeElement.toString()).
                    substring(packageName.length() + 1,typeElement.toString().length()).
                    replace(".","$");
        }
        if(isTextEmpty(packageName) || isTextEmpty(className) || isTextEmpty(fieldName)){
            throw new IllegalStateException(getLog("packageName" + packageName +
                    " ;className : " + className +
                    ";fieldName : " +fieldName +
                    "  existence null"));
        }
        return analysisField(t,packageName,className,fieldName);
    }

    public abstract D analysisField(T t,String packageName,String className,String fieldName);
}
