package com.rptools.city;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.rptools.util.Logger;
import com.rptools.util.NameUtils;
import com.rptools.util.Provider;

@Component
public class CityGen {
    private static final Logger log = Logger.getLogger(CityGen.class);
    private static final Gson gson = new Gson();
    private static Cities cityData;
    private static Random rand = new Random();

    private final NameUtils nameUtils;
    private final Provider<User> userProvider;

    @Autowired
    public CityGen(NameUtils nameUtils, Provider<User> userProvider) {
        this.nameUtils = nameUtils;
        this.userProvider = userProvider;
    }

    public void parseCityData(BufferedReader br) throws IOException {
        String str, cityDataStr = "";
        while ((str = br.readLine()) != null) {
            cityDataStr += str.trim();
        }
        br.close();

        cityData = gson.fromJson(cityDataStr, Cities.class);
    }

    public List<String> generateInns(int population) {
        if (!init) {
            try {
                parseCityData(new BufferedReader(new FileReader("resources/cityData.json")));
            } catch (IOException e) {
                log.warning("Error getting nameData.json: %e", e.getCause().toString());
            }
            init = true;
        }
        double size = Math.sqrt(Math.sqrt(population));
        List<String> inns = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            inns.add(generateInn(userProvider.get()));
        }
        return inns;
    }

    private String generateInn() {
        String inn = getNamePart(cityData.beg, user) + " " + getNamePart(cityData.end, user);
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
