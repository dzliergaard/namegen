package com.rptools.shared.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.rptools.shared.util.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name {
    private static final Logger log = Logger.getLogger(Name.class);

    private String text;
    private Long key = -1L;
    @JsonIgnore private User user;

    public Name(){}

    public Name(String text, User user){
        this.text = text;
        this.user = user;
    }

    public Name(String text, Long key){
        this.text = text;
        this.key = key;
    }

    public Name(String text, User user, Long key){
        this.text = text;
        this.user = user;
        this.key  = key;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            log.error("Error serializing name", e);
        }
        return String.format("{'text':'%s','key':'%s'}", text, key);
    }

    public Entity entity(){
        Entity entity;
        if(key > 0){
            entity = new Entity("Name", key);
        } else {
            entity = new Entity("Name");
        }
        entity.setProperty("content", text);
        entity.setProperty("user", user);
        return entity;
    }

    public static Name fromEntity(Entity entity){
        String name = (String)entity.getProperty("content");
        Long key = entity.getKey().getId();
        return new Name(name, key);
    }

    public String getText() {
        return this.text;
    }

    public User getUser() {
        return this.user;
    }

    public Long getKey() {
        return this.key;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
