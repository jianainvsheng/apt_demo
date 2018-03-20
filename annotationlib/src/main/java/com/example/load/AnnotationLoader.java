package com.example.load;

import com.example.binness.baseannotation.BaseAnnotation;
import com.example.work.IAnnotation;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by yangjian-ds3 on 2018/3/20.
 */

public class AnnotationLoader<S> implements Iterator<S>{

    public Iterator<Class<S>> mpending = null;

    public Class<S> nextName = null;

    public AnnotationLoader(List<Class<S>> annotationsClass) {
        List<Class<S>> list = new ArrayList<>();
        for(Class<S> info : annotationsClass){

            if(!list.contains(info))
                list.add(info);
        }
        this.mpending = list.iterator();
    }

    private boolean hasNextService() {
        if (nextName != null) {
            return true;
        }
        if(mpending == null || !mpending.hasNext()){
            return false;
        }
        nextName = mpending.next();
        return true;
    }

    private S nextService() {
        if (!hasNextService())
            throw new NoSuchElementException();
        Class<S> cn = nextName;
        nextName = null;
        S c = null;
        try {
            c = cn.newInstance();
            return c;
        } catch (InstantiationException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
           // e.printStackTrace();
        }
        throw new Error();
    }

    public boolean hasNext() {
        return hasNextService();
    }

    public S next() {
        return nextService();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
