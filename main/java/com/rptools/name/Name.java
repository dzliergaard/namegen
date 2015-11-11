package com.rptools.name;

import com.google.appengine.api.users.User;
import com.rptools.util.RPEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Name extends RPEntity {
    private String text;

    public Name(String text, Long key) {
        this.text = text;
        this.key = key;
    }

    public Name(String text, User user, Long key) {
        this.text = text;
        this.user = user;
        this.key = key;
    }
}
