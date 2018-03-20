package com.example.binness.baseannotation;
import com.example.work.IAnnotation;
import java.lang.annotation.Annotation;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by yangjian-ds3 on 2018/3/20.
 */

public abstract class BaseAnnotation <D,T extends Annotation> implements IAnnotation<D,T> {

    @Override
    public T getAnnotion(Element element) throws IllegalStateException {
        if(element == null){
            throw new IllegalStateException(getLog("element is null"));
        }

        Class<T> cls = getAnnotationClass();
        if(cls == null){
            throw new IllegalStateException(getLog("Class is null"));
        }

        if(getElementKind() == null){
            throw new IllegalStateException(getLog("ElementKind is null"));
        }
        ElementKind kind = getElementKind();

        if(!(kind == ElementKind.METHOD || kind == ElementKind.FIELD)){
            throw new IllegalStateException(getLog("Only method and field are currently supported"));
        }
        if(kind == ElementKind.METHOD){
            if(!(element instanceof ExecutableElement)){
                throw new IllegalStateException(getLog("element is not instanceof ExecutableElement"));
            }
        }

        T t = element.getAnnotation(cls);
        if(t == null){
            throw new IllegalStateException(getLog("getAnnotation null"));
        }

        Element enElement = element.getEnclosingElement();
        if(enElement == null || !(enElement instanceof TypeElement)){

            throw new IllegalStateException(getLog("enElement is null or enElement instanceof is not TypeElement"));
        }
        return t;
    }

    public String getLog(String message){

        Class<T> cls = getAnnotationClass();
        return ((cls == null ? "" : cls.getCanonicalName())
                +" :" + message);
    }

    public String getPackageName(Elements elementUtils,TypeElement type) throws IllegalStateException{

        if(elementUtils == null || type == null)
            throw new IllegalStateException(getLog("elementUtils or type is null"));
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    public boolean isTextEmpty(String s){

        if(s == null || "".equals(s))
            return true;
        return false;
    }
}
