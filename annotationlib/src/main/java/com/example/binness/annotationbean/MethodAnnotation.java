package com.example.binness.annotationbean;

import com.example.binness.baseannotation.ListDataAnnotation;
import com.example.model.MethodData;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public abstract class MethodAnnotation<D extends MethodData,T extends Annotation> extends ListDataAnnotation<D ,T> {

    @Override
    public ElementKind getElementKind() {
        return ElementKind.METHOD;
    }

    @Override
    public D analysis(T t, Element element, Elements elementUtils) throws IllegalStateException {

        ExecutableElement executableElement = (ExecutableElement) element;
        Set<Modifier> modifiers = executableElement.getModifiers();
        boolean isPublic = false;
        boolean isStatic = false;
        Iterator<Modifier> iterator = modifiers.iterator();
        while (iterator.hasNext()){

            Modifier modifier = iterator.next();
            if(modifier == Modifier.STATIC){
                isStatic = true;
            }

            if(modifier == Modifier.PUBLIC){

                isPublic = true;
            }
        }
        if(!isPublic){
            throw new IllegalStateException(getLog("this is not a public method"));
        }

        if(!isStatic){
            throw new IllegalStateException(getLog("this is not a static method"));
        }
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = getPackageName(elementUtils,typeElement);
        String className = typeElement.getSimpleName().toString();
        String methodName = element.getSimpleName().toString();

        //处理匿名内部类的包名地址
        if(!(packageName + "."+ className).equals(typeElement.toString()) &&
                packageName.length() - 1 <  typeElement.toString().length()){

            className = (typeElement.toString()).
                    substring(packageName.length() + 1,typeElement.toString().length()).
                    replace(".","$");
        }
        if(isTextEmpty(packageName) || isTextEmpty(className) || isTextEmpty(methodName)){
            throw new IllegalStateException(getLog("packageName" + packageName +
                    " ;className : " + className +
                    ";methodName : " +methodName +
                    "  existence null"));
        }
        return analysisMethod(t,packageName,className,methodName);
    }

    public abstract D analysisMethod(T t,String packageName,String className,String methodName);
}
