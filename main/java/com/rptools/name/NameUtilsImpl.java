package com.rptools.name;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.util.DataStoreQuery;
import com.rptools.util.Logger;
import com.rptools.util.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("request")
public class NameUtilsImpl implements NameUtils {
    private static final Logger log = Logger.getLogger(NameUtilsImpl.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Function<Entity, Name> entityToName = new Function<Entity, Name>() {
        public Name apply(Entity entity) {
            return Name.fromEntity(entity);
        }
    };

    private DataStoreQuery dataStoreQuery;
    private NameGen nameGen;
    private Provider<User> userProvider;

    @Autowired
    public NameUtilsImpl(DataStoreQuery dataStoreQuery, NameGen nameGen, Provider<User> userProvider) {
        this.dataStoreQuery = dataStoreQuery;
        this.nameGen = nameGen;
        this.userProvider = userProvider;
    }

    @Override
    public List<Name> get() {
        try {
            List<Entity> names = datastore.prepare(dataStoreQuery.getQuery("Name", "content")).asList(
                    FetchOptions.Builder.withDefaults());
            return Lists.transform(names, entityToName);
        } catch (NullPointerException npe){
            return Lists.newArrayList();
        }
    }

    @Override
    public Name get(Long keyStr, User user) throws EntityNotFoundException {
        Key key = KeyFactory.createKey("Name", keyStr);
        Name name = entityToName.apply(datastore.get(key));
        if (name == null || !user.equals(name.user)) {
            throw new EntityNotFoundException(key);
        }
        return name;
    }

    @Override
    public void save(Name name) {
        name.user = userProvider.get();
        log.info("Saving name " + name.entity());
        Key key = datastore.put(name.entity());
        name.key = key.getId();
    }

    @Override
    public void delete(Name name) {
        datastore.delete(name.entity().getKey());
    }

    /**
     * Name Generation utils
     */
    @Override
    public Name generateName() {
        Name name = new Name();
        name.text = nameGen.makeName();
        name.user = userProvider.get();
        return name;
    }

    @Override
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
