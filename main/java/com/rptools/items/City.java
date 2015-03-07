package com.rptools.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.rptools.util.Logger;
import com.rptools.util.WeightedList;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unchecked")
public class City {
    private static final Logger log = Logger.getLogger(City.class);
    private static final Random rand = new Random();
    private String name;
    private String ruler;
    private Population population;
    private List<String> inns;
    private Long key;
    @JsonIgnore private User user;

    @java.beans.ConstructorProperties({"name", "ruler", "population", "inns", "key", "user"})
    public City(String name, String ruler, Population population, List<String> inns, Long key, User user) {
        this.name = name;
        this.ruler = ruler;
        this.population = population;
        this.inns = inns;
        this.key = key;
        this.user = user;
    }

    public static City fromJson(String jsonStr) throws IOException {
        return new ObjectMapper().reader(City.class).readValue(jsonStr);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            log.error("Error serializing city", e);
        }
        return String.format("{'name':'%s','ruler':'%s'}", name, ruler);
    }

    public Entity entity() {
        Entity entity = new Entity("City", key);
        entity.setProperty("name", name);
        entity.setProperty("ruler", ruler);
        entity.setProperty("population", population);
        entity.setProperty("inns", inns);
        entity.setProperty("user", user);
        return entity;
    }

    public static City fromEntity(Entity entity){
        String name = (String)entity.getProperty("name");
        String ruler = (String)entity.getProperty("ruler");
        Population population = (Population)entity.getProperty("population");
        List<String> inns = (List<String>)entity.getProperty("inns");
        Long key = entity.getKey().getId();
        User user = (User)entity.getProperty("user");
        return new City(name, ruler, population, inns, key, user);
    }

    public String getName() {
        return this.name;
    }

    public String getRuler() {
        return this.ruler;
    }

    public Population getPopulation() {
        return this.population;
    }

    public List<String> getInns() {
        return this.inns;
    }

    public Long getKey() {
        return this.key;
    }

    public User getUser() {
        return this.user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRuler(String ruler) {
        this.ruler = ruler;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void setInns(List<String> inns) {
        this.inns = inns;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Population {
        public List<RacePop> people = Lists.newArrayList();
        public int tot = 0;
        public String searchMod;
        // search mods are static numbers based on population
        private static WeightedList<String> searchMods = new WeightedList<String>() {{
            this.add(250, "-6");
            this.add(500, "-4");
            this.add(1500, "-2");
            this.add(4500, "0");
            this.add(6750, "+1");
            this.add(26500, "+3");
            this.add(34500, "+5");
        }};

        public Population() {}

        public void add(Race race, CityTemplate template, Diversity diversity) {
            if (people.contains(new RacePop(race))) {
                return;
            }
            int population = template.pop();
            population *= Math.pow(diversity.mod(), people.size());
            people.add(new RacePop(race, population));
            tot += population;
            searchMod = searchMods.get(tot);
        }
//        public List<RacePop> getPeople() {
//            return people;
//        }
//
//        public int getTot() {
//            return this.tot;
//        }
//
//        public String getSearchMod() {
//            return this.searchMod;
//        }
//
//        public void setPeople(List<RacePop> people) {
//            this.people = people;
//        }
//
//        public void setTot(int tot) {
//            this.tot = tot;
//        }
//
//        public void setSearchMod(String searchMod) {
//            this.searchMod = searchMod;
//        }
    }

    public static class RacePop {
        public Race race;
        public int population;
        public RacePop(Race race, int population){
            this.race = race;
            this.population = population;
        }
        public RacePop(Race race){ this.race = race; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RacePop)) return false;

            RacePop racePop = (RacePop) o;
            return race == racePop.race;
        }
    }

    public static enum CityTemplate {
        Village(50, 500),
        Town(500, 5000),
        City(5000, 25000),
        Capital(25000, 75000);

        private int min, max;

        CityTemplate(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int pop() {
            return rand.nextInt(max - min) + min;
        }

        public static CityTemplate rand() {
            return values()[rand.nextInt(values().length)];
        }

        public static CityTemplate get(String s) {
            try {
                return valueOf(s);
            } catch (Exception e) {
                return rand();
            }
        }
    }

    public static enum Race {
        Human,
        Elf,
        Dwarf,
        Gnome,
        Orc,
        Goliath,
        Dragonborn,
        Halfling;

        public static Race rand() {
            return values()[rand.nextInt(values().length)];
        }

        public static Race get(String s) {
            try {
                return valueOf(s);
            } catch (Exception e) {
                return rand();
            }
        }
    }

    public enum Diversity {
        Low(.1),
        Medium(.2),
        High(.3),
        Equal(.4);

        private double modifier;

        Diversity(double modifier) {
            this.modifier = modifier;
        }

        public double mod() {
            return modifier;
        }

        public static Diversity rand() {
            return values()[rand.nextInt(values().length)];
        }

        public static Diversity get(String s) {
            try {
                return valueOf(s);
            } catch (Exception e) {
                return rand();
            }
        }
    }
}
