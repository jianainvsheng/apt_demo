package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Test1Annotation {

    String test() default "I am Test1Annotation";
}
