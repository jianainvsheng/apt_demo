package com.example.binness;

import com.example.annotation.Test1Annotation;
import com.example.binness.annotationbean.MethodAnnotation;
import com.example.model.MethodData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public class Test1 extends MethodAnnotation<MethodData,Test1Annotation> {


    @Override
    public Class<Test1Annotation> getAnnotationClass(){
        return Test1Annotation.class;
    }

    @Override
    public MethodData analysisMethod(Test1Annotation test1Annotation, String packageName, String className, String methodName) {
        MethodData data = new MethodData();
        data.medthodName = methodName;
        data.packgetName = packageName;
        data.className = className;
        data.test = test1Annotation.test();
        System.out.println("===========APT========getMapDataKey : " + getMapDataKey(data));
        return data;
    }

    @Override
    public String getMapDataKey(MethodData data) {
        return data.medthodName + "_" +
                data.packgetName+ "_" +
                data.className + "_" +
                data.test;
    }
}
