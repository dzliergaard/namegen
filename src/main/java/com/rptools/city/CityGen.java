package com.rptools.city;

import java.util.List;
import java.util.Random;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.rptools.name.Name;
import com.rptools.name.NameGen;
import com.rptools.s3.CityFileParser;

@Component
@CommonsLog
public class CityGen {
    private static final Random rand = new Random();
    private final Cities cityData;
    private final NameGen nameGen;

    @Autowired
    public CityGen(CityFileParser cityFileParser, NameGen nameGen) {
        this.nameGen = nameGen;
        cityData = cityFileParser.parseFile("cityData", Cities.class);
    }

    public City generateCity(CityTemplate template, Diversity diversity, Species species) {
        City city = new City();
        // generate name for city and ruler in same call
        List<Name> names = nameGen.generateNames(2);
        if (rand.nextDouble() < .2) {
            city.setName(names.get(0).getText());
        } else {
            city.setName(names.get(0).getText().split(" ")[0]);
        }
        city.getPopulation().add(species, template, diversity);
        // add 3 races, or sometimes 2 for low-diversity places
        while (city.getPopulation().completePop(diversity)) {
            city.getPopulation().add(Species.rand(), template, diversity);
        }

        city.setRuler(new Ruler(names.get(1), Species.valueOf(city.getPopulation().getWeightedRace())));

        city.setInns(generateInns(city.getPopulation().getTot()));
        return city;
    }

    private List<String> generateInns(int population) {
        double size = Math.sqrt(Math.sqrt(population));
        List<String> inns = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            inns.add(generateInn());
        }
        return inns;
    }

    private String generateInn() {
        String inn = getNamePart(cityData.getBeg()) + " " + getNamePart(cityData.getEnd());
        if (inn.matches(".*\\{-\\}.*")) {
            inn = inn.replace("{-}", "");
        } else {
            inn = "The " + inn;
        }
        return inn;
    }

    private String getNamePart(List<String> list) {
        String name = getFrom(list, false);
        while (name.matches(".*\\{[^-]\\}.*")) {
            name = name.replaceFirst("\\{1\\}", getFrom(cityData.getBeg(), true));
            name = name.replaceFirst("\\{2\\}", getFrom(cityData.getEnd(), true));
            name = name.replaceFirst("\\{n\\}", nameGen.generateNames(1).get(0).getText().split(" ")[0]);
        }
        return name;
    }

    private String getFrom(List<String> list, boolean noTemplates) {
        if (!noTemplates) {
            return list.get(rand.nextInt(list.size()));
        }
        String ret = list.get(rand.nextInt(list.size()));
        while (ret.matches(".*\\{.\\}.*")) {
            ret = list.get(rand.nextInt(list.size()));
        }
        return ret;
    }
}
