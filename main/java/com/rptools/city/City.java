package com.rptools.city;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class City {
    private static final Gson gson = new Gson();
    public String name;
    public String ruler;
    public Population population = new Population();
    public List inns = Lists.newArrayList();
    public Long key;
    public transient User user;

    public Entity entity() {
        Entity entity = new Entity("City", key);
        entity.setProperty("name", name);
        entity.setProperty("ruler", ruler);
        entity.setProperty("population", population);
        entity.setProperty("inns", inns);
        entity.setProperty("user", user);
        return entity;
    }

    public static City fromEntity(Entity entity) {
        City city = new City();
        city.name = (String) entity.getProperty("name");
        city.ruler = (String) entity.getProperty("ruler");
        city.population = (Population) entity.getProperty("population");
        city.inns = (List) entity.getProperty("inns");
        city.key = entity.getKey().getId();
        city.user = (User) entity.getProperty("user");
        return city;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
