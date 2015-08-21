package com.rptools.city;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.name.Name;
import com.rptools.name.NameUtils;
import com.rptools.util.DataStoreQuery;
import com.rptools.util.Logger;

@Component
public class CityUtils {
    private static final Logger log = Logger.getLogger(CityUtils.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Random rand = new Random();
    private static Function<Entity, City> entityToCity = new Function<Entity, City>() {
        public City apply(Entity entity) {
            return City.fromEntity(entity, City.class);
        }
    };

    private DataStoreQuery dataStoreQuery;
    private CityGen cityGen;
    private NameUtils nameUtils;

    @Autowired
    public CityUtils(DataStoreQuery dataStoreQuery, CityGen cityGen, NameUtils nameUtils) {
        this.dataStoreQuery = dataStoreQuery;
        this.cityGen = cityGen;
        this.nameUtils = nameUtils;
    }

    public List<City> get() {
        List<Entity> cities = datastore.prepare(dataStoreQuery.getQuery("City")).asList(
            FetchOptions.Builder.withDefaults());
        return Lists.transform(cities, entityToCity);
    }

    public void save(City city) {
        log.info("Saving city %s", city.entity());
        datastore.put(city.entity());
    }

    public City generateCity(CityTemplate template, Diversity diversity, Race race) {
        City city = new City();
        // generate name for city and ruler in same call
        List<Name> names = nameUtils.generateNames(2);
        if (rand.nextDouble() < .2) {
            city.name = names.get(0).text;
        } else {
            city.name = names.get(0).text.split(" ")[0];
        }
        city.population.add(race, template, diversity);
        // add 3 races, or sometimes 2 for low-diversity places
        while (city.population.completePop(diversity)) {
            city.population.add(Race.rand(), template, diversity);
        }

        city.ruler = String.format("%s (%s)", names.get(1).text, city.population.getWeightedRace());

        city.inns = cityGen.generateInns(city.population.tot);
        return city;
    }
}
