package com.rptools.name;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.gson.Gson;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrainingName extends Name {
    private static final Gson GSON = new Gson();
    private NameAttribute attribute;

    public TrainingName(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
