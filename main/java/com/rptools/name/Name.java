package com.rptools.name;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.gson.Gson;

public class Name implements Serializable {
    private static final Gson gson = new Gson();
    public String text;
    public Long key = -1L;
    public transient User user;

    public Name() {
    }

    public Name(String text, Long key) {
        this.text = text;
        this.key = key;
    }

    public Name(String text, User user, Long key) {
        this.text = text;
        this.user = user;
        this.key = key;
    }

    public Entity entity() {
        Entity entity;
        if (key > 0) {
            entity = new Entity("Name", key);
        } else {
            entity = new Entity("Name");
        }
        entity.setProperty("content", text);
        entity.setProperty("user", user);
        return entity;
    }

    public static Name fromEntity(Entity entity) {
        String name = (String) entity.getProperty("content");
        Long key = entity.getKey().getId();
        return new Name(name, key);
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
