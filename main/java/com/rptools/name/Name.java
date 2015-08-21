package com.rptools.name;

import com.google.appengine.api.users.User;
import com.rptools.util.RPEntity;

public class Name extends RPEntity {
    public String text;

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

    @Override
    public String getType() {
        return "Name";
    }
}
