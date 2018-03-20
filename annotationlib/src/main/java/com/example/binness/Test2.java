package com.example.binness;
import com.example.annotation.Test2Annotation;
import com.example.binness.annotationbean.FieldAnnotation;
import com.example.model.FieldData;
import java.util.Map;
/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public class Test2 extends FieldAnnotation<FieldData,Test2Annotation> {

    @Override
    public Class<Test2Annotation> getAnnotationClass(){
        return Test2Annotation.class;
    }

    @Override
    public FieldData analysisField(Test2Annotation test2Annotation, String packageName, String className, String fieldName) {
        FieldData data = new FieldData();
        data.fieldName = fieldName;
        data.packgetName = packageName;
        data.className = className;
        data.test = test2Annotation.test();
        return data;
    }

    @Override
    public String getMapDataKey(FieldData data) {
        return  data.fieldName + "_" +
                data.packgetName+ "_" +
                data.className + "_" +
                data.test;
    }
}
