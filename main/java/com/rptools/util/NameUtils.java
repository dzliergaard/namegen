package com.rptools.util;

import java.util.List;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.items.Name;
import com.rptools.items.NameAttribute;
import com.rptools.items.NameGenData;
import com.rptools.items.TrainingName;

public class NameUtils {

    private static final Logger log = Logger.getLogger(NameUtils.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static List<Name> get(User user) {
        Query.Filter userEq = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, user);
        Query.Filter usernameEq = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, user.getEmail());
        Query.Filter userOr = new Query.CompositeFilter(Query.CompositeFilterOperator.OR, Lists.newArrayList(
            userEq,
            usernameEq));
        Query query = new Query("Name").setFilter(userOr).addSort("content");
        List<Entity> names = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        return Lists.transform(names, entityToName);
    }

    public static Name get(Long keyStr, User user) throws EntityNotFoundException {
        Key key = KeyFactory.createKey("Name", keyStr);
        Name name = entityToName.apply(datastore.get(key));
        if (name == null || !user.equals(name.getUser())) {
            throw new EntityNotFoundException(key);
        }
        return name;
    }

    public static void save(Name name) {
        log.info("Saving name " + name.entity());
        Key key = datastore.put(name.entity());
        name.setKey(key.getId());
    }

    public static void delete(Name name) {
        datastore.delete(name.entity().getKey());
    }

    private static Function<Entity, Name> entityToName = new Function<Entity, Name>() {

        @Override
        public Name apply(Entity entity) {
            return Name.fromEntity(entity);
        }
    };

    /**
     * Name Generation utils
     */
    public static Name generateName(User user) {
        return new Name(NameGenData.makeName(), user);
    }

    public static List<Name> generateNames(int numNames, User user, NameAttribute attribute) {
        List<Name> names = Lists.newArrayList();
        while (numNames-- > 0) {
            names.add(new Name(NameGenData.makeName(attribute), user));
        }
        return names;
    }

    public static TrainingName getTrainingName() {
        return NameGenData.getTrainingName();
    }

    public static void train(TrainingName name) {
        if (name.getAttribute() == NameAttribute.SKIP) {
            return;
        }
        log.info("Training name " + name.entity());
        NameGenData.train(name);
    }
}