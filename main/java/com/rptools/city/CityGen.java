package com.rptools.city;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.rptools.name.NameUtils;
import com.rptools.util.Logger;

@Component
public class CityGen {
    private static Random rand = new Random();
    private Cities cityData;

    private NameUtils nameUtils;

    @Autowired
    public CityGen(CityFileParser cityFileParser, NameUtils nameUtils) {
        this.nameUtils = nameUtils;
        Stopwatch timer = Stopwatch.createStarted();
        cityData = cityFileParser.parseCityFile("resources/cityData.json");
        timer.stop();
        Logger.getLogger(CityGen.class).info(
            "City data parsed in %d milliseconds.",
            timer.elapsed(TimeUnit.MILLISECONDS));
    }

    public List<String> generateInns(int population) {
        double size = Math.sqrt(Math.sqrt(population));
        List<String> inns = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            inns.add(generateInn());
        }
        return inns;
    }

    private String generateInn() {
        String inn = getNamePart(cityData.beg) + " " + getNamePart(cityData.end);
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
            name = name.replaceFirst("\\{1\\}", getFrom(cityData.beg, true));
            name = name.replaceFirst("\\{2\\}", getFrom(cityData.end, true));
            name = name.replaceFirst("\\{n\\}", nameUtils.generateName().text.split(" ")[0]);
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
