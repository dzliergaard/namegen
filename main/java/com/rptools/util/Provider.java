package com.rptools.util;

import com.google.common.base.Supplier;

public class Provider<T> implements Supplier<T> {
    private T t;

    public Provider(){
        super();
    }

    public Provider(T t){
        this.t = t;
    }

    @Override
    public T get() {
        return t;
    }

    public void set(T t){
        this.t = t;
    }

    public void empty(){ this.t = null; }

    public boolean has(){
        return this.t != null;
    }
}
