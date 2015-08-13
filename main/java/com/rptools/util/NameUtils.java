package com.rptools.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.name.Name;
import com.rptools.name.NameGen;

@Component
@Scope("session")
public class NameUtils {
    private static final Logger log = Logger.getLogger(NameUtils.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Function<Entity, Name> entityToName = new Function<Entity, Name>() {
        public Name apply(Entity entity) {
            return Name.fromEntity(entity);
        }
    };

    private final DataStoreQuery dataStoreQuery;
    private final NameGen nameGen;
    private final Provider<User> userProvider;

    @Autowired
    public NameUtils(DataStoreQuery dataStoreQuery, NameGen nameGen, Provider<User> userProvider) {
        this.dataStoreQuery = dataStoreQuery;
        this.nameGen = nameGen;
        this.userProvider = userProvider;
    }

    public List<Name> get() {
        if (!userProvider.has()) {
            return Lists.newArrayList();
        }
        List<Entity> names = datastore.prepare(dataStoreQuery.getQuery("Name", "content")).asList(
            FetchOptions.Builder.withDefaults());
        return Lists.transform(names, entityToName);
    }

    public Name get(Long keyStr, User user) throws EntityNotFoundException {
        Key key = KeyFactory.createKey("Name", keyStr);
        Name name = entityToName.apply(datastore.get(key));
        if (name == null || !user.equals(name.user)) {
            throw new EntityNotFoundException(key);
        }
        return name;
    }

    public void save(Name name) {
        name.user = userProvider.get();
        log.info("Saving name " + name.entity());
        Key key = datastore.put(name.entity());
        name.key = key.getId();
    }

    public void delete(Name name) {
        datastore.delete(name.entity().getKey());
    }

    /**
     * Name Generation utils
     */
    public Name generateName(User user) {
        Name name = new Name();
        name.text = nameGen.makeName();
        name.user = user;
        return name;
    }

    public List<Name> generateNames(int numNames) {
        List<Name> names = Lists.newArrayList();
        while (numNames-- > 0) {
            Name name = new Name();
            name.text = nameGen.makeName();
            name.user = userProvider.get();
            names.add(name);
        }
        return names;
    }
}
