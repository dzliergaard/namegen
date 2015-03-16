package com.rptools.items;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Entity;

public class TrainingName {
    private String name;
    private NameAttribute attribute;

    public TrainingName() {
    }

    public TrainingName(String name) {
        this.name = name;
    }

    public TrainingName(String name, NameAttribute attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NameAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(NameAttribute attribute) {
        this.attribute = attribute;
    }

    public Entity entity(){
        Entity entity;
        entity = new Entity("TrainingName");
        entity.setProperty("content", name);
        entity.setProperty("attribute", attribute.name());
        return entity;
    }

    public String toString(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static TrainingName fromEntity(Entity e){
        String name = (String)e.getProperty("content");
        NameAttribute attribute = NameAttribute.valueOf((String)e.getProperty("attribute"));
        return new TrainingName(name, attribute);
    }
}
