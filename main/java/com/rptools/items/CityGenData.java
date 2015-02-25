package com.rptools.items;

import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.rptools.util.Logger;
import com.rptools.util.NameUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by DZL on 2/22/14.
 */
public class CityGenData {
    private static final Logger log = Logger.getLogger(CityGenData.class);
    private static CityData cityData;
    private static Random rand = new Random();
    private static boolean init = false;

    private static class CityData{
        List<String> beg;
        List<String> end;

        public List<String> getBeg() { return beg; }
        public List<String> getEnd() { return end; }
        public void setBeg(List<String> beg) { this.beg = beg; }
        public void setEnd(List<String> end) { this.end = end; }
    }

    public static void parseCityData(BufferedReader br) throws IOException {
        String str, cityDataStr = "";
        while ((str = br.readLine()) != null) {
            cityDataStr += str.trim();
        }
        br.close();

        cityData = new ObjectMapper().reader(CityData.class).readValue(cityDataStr);
    }

    public static List<String> generateInns(int population, User user) {
        if(!init){
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
            inns.add(generateInn(user));
        }
        return inns;
    }

    private static String generateInn(User user) {
        String inn = getNamePart(cityData.getBeg(), user) + " " + getNamePart(cityData.getEnd(), user);
        if (inn.matches(".*\\{-\\}.*")) {
            inn = inn.replace("{-}", "");
        } else {
            inn = "The " + inn;
        }
        return inn;
    }

    private static String getNamePart(List<String> list, User user) {
        String name = getFrom(list, false);
        while (name.matches(".*\\{[^-]\\}.*")) {
            name = name.replaceFirst("\\{1\\}", getFrom(cityData.getBeg(), true));
            name = name.replaceFirst("\\{2\\}", getFrom(cityData.getEnd(), true));
            name = name.replaceFirst("\\{n\\}", NameUtils.generateName(user).getText().split(" ")[0]);
        }
        return name;
    }

    private static String getFrom(List<String> list, boolean noTemplates) {
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
