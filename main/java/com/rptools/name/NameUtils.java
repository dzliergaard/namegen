package com.rptools.name;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;

import java.util.List;

public interface NameUtils {
    List<Name> get();

    Name get(Long keyStr, User user) throws EntityNotFoundException;

    void save(Name name);

    void delete(Name name);

    Name generateName();

    List<Name> generateNames(int numNames);
}
