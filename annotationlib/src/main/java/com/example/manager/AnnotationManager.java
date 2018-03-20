package com.example.manager;
import com.example.work.IAnnotation;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.management.AttributeList;

/**
 * Created by yangjian-ds3 on 2018/3/19.
 */

public class AnnotationManager {

    private Map<Class<? extends Annotation>,IAnnotation<?,? extends Annotation>> mAnnotationMap;

    private List<Class<? extends Annotation>> mAnnotationClassList;

    public AnnotationManager() throws IllegalStateException{
        mAnnotationMap = new HashMap<>();
//        ServiceLoader<IAnnotation> loader = ServiceLoader.load(IAnnotation.class);
//        Iterator<IAnnotation> iterator = loader.iterator();

        Iterator<IAnnotation> iterator = AnnotationLoadManager.load();
        while (iterator.hasNext()){
            IAnnotation annotation = iterator.next();
            Class<? extends Annotation> cls = annotation.getAnnotationClass();
            if(annotation != null && cls != null && !mAnnotationMap.containsKey(cls)){
                mAnnotationMap.put(cls,annotation);
            }else{
                throw new IllegalStateException("IAnnotation is null");
            }
        }
    }

    /**
     * clear the datas
     */
    public void clearData(){
        if(mAnnotationMap != null && mAnnotationMap.size() > 0){
            for(Class<? extends Annotation> key : mAnnotationMap.keySet()){
                IAnnotation iAnnotation = mAnnotationMap.get(key);
                iAnnotation.clearData();
            }
        }
    }

    /**
     * analysis the Annotation
     * @param cls
     * @param element
     * @param elementUtils
     * @throws IllegalStateException
     */
    public void analysisAnnotation(Class<? extends Annotation> cls,Element element,Elements elementUtils) throws IllegalStateException{

        if (cls == null){
            throw new IllegalStateException("IAnnotation is null");
        }
        if(mAnnotationMap.containsKey(cls)){
            try{
                IAnnotation iAnnotation = mAnnotationMap.get(cls);
                Annotation annotation = iAnnotation.getAnnotion(element);
                iAnnotation.analysisAnnotation(annotation,element,elementUtils);
            }catch (Exception e){
                throw e;
            }
        }else{
            throw new IllegalStateException("Annotation.Class : " + cls.getSimpleName()
                    + "is not in the ServiceLoader");
        }
    }

    /**
     * use the method after analysisAnnotation
     * @param cls
     * @return
     */
    public Object getAnnotationData(Class<? extends Annotation> cls){

        Object object = null;
        if(mAnnotationMap.containsKey(cls)){
            IAnnotation iAnnotation = mAnnotationMap.get(cls);
            object = iAnnotation.getData();
        }
        return object;
    }

    public List<Object> getAllAnnotationData(){

        if(mAnnotationMap == null || mAnnotationMap.size() <= 0){

            return null;
        }
        List<Object> datas = new ArrayList<>();
        Object ob = null;
        for(Class<? extends Annotation> key : mAnnotationMap.keySet()){
            ob = getAnnotationData(key);
            datas.add(ob);
        }
        return datas;
    }
    public Set<String> getSupportedAnnotations(){
        Set<String> set = new LinkedHashSet<>();
        mAnnotationClassList = new ArrayList<>();
        for(Class<? extends Annotation> cls : mAnnotationMap.keySet()){
            set.add(cls.getCanonicalName());
            mAnnotationClassList.add(cls);
        }
        System.out.println("================mAnnotationMap size : " + mAnnotationMap.size());
        return set;
    }

    public List<Class<? extends Annotation>> getAnnotationClassList(){

        return mAnnotationClassList;
    }

    /**
     * execution the class which the dataList Stored
     */
    public void executionClassFile(String packgetName,String className,
                                   String fieldName,List<?> dataList){

       if(dataList == null || dataList.size() <= 0){
           System.out.println("================dataList=====is null==");
           return;
       }

       System.out.println("===============dataList : " + dataList.size());
    }
}
