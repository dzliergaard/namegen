package com.rptools.server;

import org.springframework.web.servlet.ModelAndView;

import com.rptools.util.RPEntity;

public abstract class EntityServerBase<T extends RPEntity> {
    public abstract ModelAndView get();

    public abstract T save(T entity);

    public abstract void delete(T entity);

}
