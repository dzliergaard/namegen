package com.rptools.items;

public class TrainingName {

    private String name;
    private NameAttribute attribute;
    private boolean sounds;

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

    public boolean isSounds() {
        return sounds;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }
}
