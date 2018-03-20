package com.example.manager;

import com.example.binness.Test1;
import com.example.binness.Test2;
import com.example.load.AnnotationLoader;
import com.example.work.IAnnotation;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yangjian-ds3 on 2018/3/20.
 */

public class AnnotationLoadManager {


    //need jdk 1.8 or more version
    public static final List<Class<? extends IAnnotation>> IANNOTATIONLIST = Arrays.asList(

            Test1.class, Test2.class
    );

    public static AnnotationLoader<IAnnotation> load() {

        return new AnnotationLoader(IANNOTATIONLIST);
    }
}
