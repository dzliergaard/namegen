package com.rptools.util;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rptools.items.City;
import com.rptools.items.CityGenData;
import com.rptools.items.Name;

import java.util.List;
import java.util.Random;

/**
 * Created by DZL on 2/15/14.
 */
public class CityUtils {
    private static final Logger log = Logger.getLogger(CityUtils.class);
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Random rand = new Random();

    public static List<City> get(User user) {
        Query.Filter userEq = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, user);
        Query.Filter userEqStr = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, user.toString());
        Query.Filter userNu = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, null);
        Query.Filter userOr = Query.CompositeFilterOperator.or(userEq, userNu, userEqStr);
        Query query = new Query("City").setFilter(userOr).addSort("name");
        List<Entity> cities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        return Lists.transform(cities, entityToCity);
    }

    public static void save(City city) {
        log.info("Saving city %s", city.entity());
        datastore.put(city.entity());
    }

    private static Function<Entity, City> entityToCity = new Function<Entity, City>() {
        @Override
        public City apply(Entity entity) {
            return City.fromEntity(entity);
        }
    };

    public static City generateCity(City.CityTemplate template, City.Diversity diversity, City.Race race, User user) {
        List<Name> names = NameUtils.generateNames(2, user);
        String name;
        if (rand.nextDouble() < .2) {
            name = names.get(0).getText();
        } else {
            name = names.get(0).getText().split(" ")[0];
        }
        City.Population pop = new City.Population();
        pop.add(race, template, diversity);
        // add 3 races, or sometimes 2 for low-diversity places
        while (pop.people.size() < 3 && (pop.people.size() < 2 || rand.nextDouble() * .2 < diversity.mod())) {
            pop.add(City.Race.rand(), template, diversity);
        }
        // determine ruler's race with a list
        // weighted in favor of more common races
        WeightedList<City.Race> races = new WeightedList<City.Race>();
        for (City.RacePop rp : pop.people) {
            races.add(rp.population * 3, rp.race);
        }
        String ruler = String.format("%s (%s)", names.get(1).getText(), races.random().name());
        List<String> inns = CityGenData.generateInns(pop.tot, user);
        return new City(name, ruler, pop, inns, null, user);
    }
}
