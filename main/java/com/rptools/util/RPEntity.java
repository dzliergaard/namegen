package com.rptools.util;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

public class RPEntity<T> implements Serializable {
    protected static final Gson gson = new Gson();
    protected Long key = -1L;
    protected transient User user;

    public RPEntity() {
    }

    public static <T extends RPEntity> T fromEntity(Entity entity, Class<T> clazz) {
        T t = gson.fromJson((String) entity.getProperty("content"), clazz);
        t.key = entity.getKey().getId();
        return t;
    }

    public Entity entity() {
        Entity entity;
        if (key > 0) {
            entity = new Entity(getType(), key);
        } else {
            entity = new Entity(getType());
        }
        entity.setProperty("content", toString());
        entity.setProperty("user", user);
        return entity;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Long getKey(){
        return key;
    }

    public void setKey(Long key){
        this.key = key;
    }
}