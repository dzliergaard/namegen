package com.rptools.city;

import com.google.common.collect.Lists;

import com.rptools.city.City.Ruler;
import com.rptools.io.CityFileParser;
import com.rptools.name.NameGen;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@CommonsLog
public class CityGen {

  private static final Random rand = new Random();
  private final Cities cityData;
  private final NameGen nameGen;

  @Autowired
  public CityGen(CityFileParser cityFileParser, NameGen nameGen) {
    this.nameGen = nameGen;
    cityData = cityFileParser.parseFile("cityData.json");
  }

  public City generateCity(Double size, Double diversity, Species species) {
    City city = new City();
    // generate name for city and ruler in same call
    List<String> names = nameGen.generateNames(2);
    if (rand.nextDouble() < .2) {
      city.setName(names.get(0));
    } else {
      city.setName(names.get(0).split(" ")[0]);
    }

    city.getPopulation().add(size, diversity, species);
    // add 3 races, or sometimes 2 for low-diversity places
    while (city.getPopulation().incompletePop(diversity)) {
      city.getPopulation().add(size, diversity, Species.rand());
    }

    city.setRuler(new Ruler(names.get(1), Species.valueOf(city.getPopulation().getWeightedRace())));

    city.setInns(generateInns(city.getPopulation().getTot()));
    city.setGuilds(generateGuilds(city.getPopulation().getTot()));
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
    String inn =
        getFrom(cityData.getInns().getBegPat()) + " " + getFrom(cityData.getInns().getEndPat());
    while (inn.matches(".*\\{[^-]\\}.*")) {
      inn = inn.replaceFirst("\\{a\\}", getFrom(cityData.getInns().getBeg()));
      inn = inn.replaceFirst("\\{n\\}", getFrom(cityData.getInns().getEnd()));
      inn = inn.replaceFirst("\\{p\\}", getName());
    }
    return inn;
  }

  private List<String> generateGuilds(int population) {
    List<String> guilds = Lists.newArrayList();
    population -= rand.nextInt(500) + 2000;
    while (population > 0) {
      population -= rand.nextInt(500) + 2000;
      guilds.add(generateGuild());
    }
    return guilds;
  }

  private String generateGuild() {
    String guild = getFrom(cityData.getGuilds().getPat());
    while (guild.matches(".*\\{[^-]\\}.*")) {
      guild = guild.replaceFirst("\\{g\\}", getFrom(cityData.getGuilds().getGroup()));
      guild = guild.replaceFirst("\\{n\\}", getFrom(cityData.getGuilds().getNoun()));
    }
    return guild;
  }

  private String getName() {
    String name = nameGen.generateNames(1).get(0);
    if (rand.nextDouble() > .2) {
      return name.split(" ")[0];
    }
    return name;
  }

  private String getFrom(List<String> list) {
    return list.get(rand.nextInt(list.size()));
  }
}
