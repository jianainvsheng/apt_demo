package com.example.processor;

import com.example.annotation.Test1Annotation;
import com.example.binness.Test1;
import com.example.binness.Test2;
import com.example.manager.AnnotationManager;
import com.example.model.FieldData;
import com.example.model.MethodData;
import com.example.work.IAnnotation;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;
    private AnnotationManager mAnnotationManager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("================APT=====init=======");
        mAnnotationManager = new AnnotationManager();
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations == null || annotations.isEmpty())
            return false;
        List<Class<? extends Annotation>> annotationClassList = mAnnotationManager.getAnnotationClassList();
        if (annotationClassList == null) {
            return false;
        }
        mAnnotationManager.clearData();
        analysisAnnotation(annotationClassList, roundEnv);
        return true;
    }

    private void analysisAnnotation(List<Class<? extends Annotation>> annotationClassList, RoundEnvironment roundEnv) {

        List<FieldData> fileDataList = new ArrayList<>();
        List<MethodData> methodDataList = new ArrayList<>();
        for (Class<? extends Annotation> cls : annotationClassList) {
            for (Element element : roundEnv.getElementsAnnotatedWith(cls)) {
                mAnnotationManager.analysisAnnotation(cls, element, mElementUtils);
            }
        }
        List<Object> allList = mAnnotationManager.getAllAnnotationData();
        for(Object object : allList){
            List<?> dataList = null;
            if(object != null && object instanceof List){
                // this is ListDataAnnotation
                dataList = (List<?>) object;
            }
            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i) instanceof MethodData) {
                        MethodData data = (MethodData) dataList.get(i);
                        methodDataList.add(data);
                    } else if (dataList.get(i) instanceof FieldData) {
                        FieldData data = (FieldData) dataList.get(i);
                        fileDataList.add(data);
                    }
                }
            }
        }


        mAnnotationManager.executionClassFile("", "", "", fileDataList);
        mAnnotationManager.executionClassFile("", "", "", methodDataList);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        return mAnnotationManager.getSupportedAnnotations();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.latestSupported();
    }
}
