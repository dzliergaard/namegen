package com.rptools.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Supplier;

@Component
@Scope("session")
public class Provider<T> implements Supplier<T> {
    private T t;

    public Provider() {
        super();
    }

    @Override
    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public void empty() {
        this.t = null;
    }

    public boolean has() {
        return this.t != null;
    }
}
