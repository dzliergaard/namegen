package com.rptools.name;

import lombok.Getter;
import lombok.Setter;

public class TrainingName extends Name {
    @Getter @Setter private NameAttribute attribute;

    public TrainingName(){
    }

    public TrainingName(String text){
        this.setText(text);
    }
}
