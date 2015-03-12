package com.rptools.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rptools.util.Logger;
import com.rptools.util.WeightedList;

public class NameGenData {
    private static final Logger log = Logger.getLogger(NameGenData.class);
    private static WeightedList<String> origFirst = new WeightedList<String>();
    private static WeightedList<String> origLast = new WeightedList<String>();
    private static WeightedNameData first;
    private static WeightedNameData last;
    private static boolean init = false;
    private static Map<String, AttributeNameData> attributeNameData;
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private static Pattern beg = Pattern.compile("^([^AEIOUY]*[AEIOUY]+[^AEIOUY]*(?:E$)?)?");
    private static Pattern mid = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]+)[AEIOUY]");
    private static Pattern end = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]*[E]?)$");
    private static Pattern namePat = Pattern.compile("[\\w]+");

    public NameGenData() {
    }

    private static Collection<TrainingName> getTrainingNamesForAttribute(NameAttribute attr){
        return null;
    }

    private static int getGroups(Matcher matcher, Map<String, Integer> groups) {
        int i = 0;
        while (matcher.find()) {
            String str = matcher.group(1);
            Integer num = groups.get(str);
            if (num == null) {
                num = 0;
            }
            num++;
            groups.put(str, num);
            i++;
        }
        return i;
    }

    private static NameData parseNames(BufferedReader br, WeightedList<String> orig) throws IOException {
        String str, nameDataStr = "";
        while ((str = br.readLine()) != null) {
            nameDataStr += str.trim();
        }
        br.close();

        Matcher nameMatcher = namePat.matcher(nameDataStr);
        Map<String, Integer> begs = Maps.newHashMap();
        Map<String, Integer> mids = Maps.newHashMap();
        Map<String, Integer> ends = Maps.newHashMap();
        double total = 0, meanTotal = 0, i = 0;
        List<Integer> syls = Lists.newArrayList();
        while (nameMatcher.find()) {
            i++;
            String name = nameMatcher.group();
            orig.add(1, name);
            int syl = getGroups(beg.matcher(name), begs);
            syl += getGroups(mid.matcher(name), mids);
            syl += getGroups(end.matcher(name), ends);
            total += syl;
            syls.add(syl);
        }
        double mean = total / i;
        // second pass to get mean total
        for (Integer s : syls) {
            double var = s - mean;
            meanTotal += var * var;
        }
        return new NameData(begs, mids, ends, mean, Math.sqrt(meanTotal / i));
    }

    private static void parseNameData() {
        try {
            // generate the normal name stacks
            first = new WeightedNameData(parseNames(
                    new BufferedReader(new FileReader("resources/names.txt")), origFirst));
            last = new WeightedNameData(parseNames(
                    new BufferedReader(new FileReader("resources/lastnames.txt")), origLast));

            // generate the attribute name lists
            for(NameAttribute attr : NameAttribute.values()){

            }
        } catch (IOException e) {
            log.warning("Error parsing name data: %e", e.getCause().toString());
        }
        init = true;
    }

    public static String makeName() {
        if (!init) {
            parseNameData();
        }
        return makeName(first) + " " + makeName(last);
    }

    private static String makeName(WeightedNameData nameData) {
        String name = nameData.beg.random();
        int syl = (int) Math.round(new Random().nextGaussian() * nameData.deviation + nameData.mean);
        if (--syl == 0) {
            return format(name);
        }
        if (--syl == 0) {
            return format(name + nameData.end.random());
        }
        while (syl-- > 0) {
            name += nameData.mid.random();
        }
        name += nameData.end.random();
        return format(name);
    }

    public static String format(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static TrainingName getTrainingName() {
        if(!init){
            parseNameData();
        }
        String name = format(origFirst.random()) + " " + format(origLast.random());
        return new TrainingName(name);
    }
}
