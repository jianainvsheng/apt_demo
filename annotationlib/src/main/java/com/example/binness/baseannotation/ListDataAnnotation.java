package com.example.binness.baseannotation;

import com.example.model.AnnotationData;
import com.example.work.IAnnotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public abstract class ListDataAnnotation<D extends AnnotationData,T extends Annotation> extends BaseAnnotation<List<D>,T> {

    private List<D> mDataList;

    private Map<String , D> mDatamap = null;

    @Override
    public void analysisAnnotation(T t, Element element,Elements elementUtils) throws IllegalStateException {
        try {
            if(mDatamap == null){
                mDatamap = new HashMap();
            }
            D data = analysis(t,element,elementUtils);
            String filterKey = getMapDataKey(data);
            if(isTextEmpty(filterKey))
                throw new IllegalStateException(getLog("the filtering key is null"));
            mDatamap.put(filterKey,data);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *
     * @param data
     * @return The method or field of filtering multiple annotations for the same object in the return value
     */
    public abstract String getMapDataKey(D data);
    /**
     *
     * @param t
     * @param element
     * @return
     */
    public abstract D analysis(T t, Element element,Elements elementUtils) throws IllegalStateException;

    @Override
    public List<D> getData() {

        if(mDatamap == null ||mDatamap.size() <= 0)
            return null;
        if(mDataList == null)
            mDataList = new ArrayList<>();
        mDataList.clear();
        for(String key : mDatamap.keySet()){
            mDataList.add(mDatamap.get(key));
        }
        return mDataList;
    }

    @Override
    public void clearData() {

        if(mDatamap != null){
            mDatamap.clear();
        }
        if(mDataList != null){
            mDataList.clear();
        }
    }
}
