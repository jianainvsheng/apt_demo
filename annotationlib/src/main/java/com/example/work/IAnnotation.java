package com.example.work;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public interface IAnnotation<D , T extends Annotation> {

    /**
     * Return the type of annotation
     *
     * @return
     */
    Class<T> getAnnotationClass();

    /**
     * return the annotation is field or method
     *
     * @return
     */
    ElementKind getElementKind();

    /**
     * Return the Annotation
     *
     * @return
     */
    T getAnnotion(Element element) throws IllegalStateException;

    /**
     * Analysis the Annotation
     *
     * @param t
     * @param element
     */
    void analysisAnnotation(T t, Element element, Elements elementUtils) throws IllegalStateException;

    /**
     * return the data which the annotation Stored 
     * @return
     */
    D getData();

    /**
     * clear the data
     */
    void clearData();

}
