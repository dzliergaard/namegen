package com.rptools.name;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
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
import com.rptools.util.DataStoreQuery;
import com.rptools.util.Logger;
import com.rptools.util.Provider;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NameUtils {
    private static final Logger log = Logger.getLogger(NameUtils.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Function<Entity, Name> entityToName = new Function<Entity, Name>() {
        public Name apply(Entity entity) {
            try {
                return Name.fromEntity(entity, Name.class);
            } catch (Exception e) {
                Name name = new Name();
                name.setText((String) entity.getProperty("content"));
                name.setKey(entity.getKey().getId());
                return name;
            }
        }
    };
    private static Function<Entity, TrainingName> entityToTrainingName = new Function<Entity, TrainingName>() {
        public TrainingName apply(Entity entity) {
            try {
                return TrainingName.fromEntity(entity, TrainingName.class);
            } catch (Exception e) {
                TrainingName name = new TrainingName();
                name.setText((String) entity.getProperty("content"));
                name.setKey(entity.getKey().getId());
                return name;
            }
        }
    };

    private DataStoreQuery dataStoreQuery;
    private NameGen nameGen;
    private Provider<User> userProvider;

    @Autowired
    public NameUtils(DataStoreQuery dataStoreQuery, NameGen nameGen, Provider<User> userProvider) {
        this.dataStoreQuery = dataStoreQuery;
        this.nameGen = nameGen;
        this.userProvider = userProvider;
    }

    public List<Name> get() {
        try {
            List<Entity> names = datastore.prepare(dataStoreQuery.getQuery("Name")).asList(
                FetchOptions.Builder.withDefaults());
            return Lists.transform(names, entityToName);
        } catch (NullPointerException npe) {
            return Lists.newArrayList();
        }
    }

    public Name get(Long keyStr, User user) throws EntityNotFoundException {
        Key key = KeyFactory.createKey("Name", keyStr);
        Name name = entityToName.apply(datastore.get(key));
        if (name == null || !user.equals(name.getUser())) {
            throw new EntityNotFoundException(key);
        }
        return name;
    }

    public void save(Name name) {
        name.setUser(userProvider.get());
        Entity entity = name.entity();
        log.info("Saving name " + entity);
        Key key = datastore.put(entity);
        name.setKey(key.getId());
    }

    public void delete(Name name) {
        datastore.delete(name.entity().getKey());
    }

    public Name generateName() {
        Name name = new Name();
        name.setText(nameGen.makeName());
        name.setUser(userProvider.get());
        return name;
    }

    public List<Name> generateNames(int numNames) {
        List<Name> names = Lists.newArrayList();
        while (numNames-- > 0) {
            Name name = new Name();
            name.setText(nameGen.makeName());
            name.setUser(userProvider.get());
            names.add(name);
        }
        return names;
    }

    public TrainingName getTrainingName(){
        return nameGen.getTrainingName();
    }

    public void train(TrainingName name) {
        datastore.put(name.entity());
    }

    private List<TrainingName> getTrainingNamesForAttribute(NameAttribute attr) {
        List<Entity> names = datastore.prepare(dataStoreQuery.getQuery("TrainingName")).asList(FetchOptions.Builder.withDefaults());
        return Lists.transform(names, entityToTrainingName);
    }
}
