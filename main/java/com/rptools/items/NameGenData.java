package com.rptools.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.*;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rptools.util.Logger;
import com.rptools.util.WeightedList;

public class NameGenData {

    private static final Logger log = Logger.getLogger(NameGenData.class);
    private static WeightedList<String> origFirst = new WeightedList<String>();
    private static WeightedList<String> origLast = new WeightedList<String>();
    private static Map<NameAttribute, AttributeNameData> attributeNameData = Maps.newHashMap();
    private static WeightedNameData first;
    private static WeightedNameData last;
    private static boolean init = false;
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private static Pattern beg = Pattern.compile("^([^AEIOUY]*[AEIOUY]+[^AEIOUY]*(?:E$)?)?");
    private static Pattern mid = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]+)[AEIOUY]");
    private static Pattern end = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]*[E]?)$");
    private static Pattern firstPat = Pattern.compile("(\\w*)[ ]");
    private static Pattern lastPat = Pattern.compile("[ ](\\w*)");
    private static Pattern namePat = Pattern.compile("[\\w]+");

    private static Function<Entity, TrainingName> entityToTrainingName = new Function<Entity, TrainingName>() {

        @Override
        public TrainingName apply(Entity entity) {
            return TrainingName.fromEntity(entity);
        }
    };

    public NameGenData() {
    }

    private static List<TrainingName> getTrainingNamesForAttribute(NameAttribute attr) {
        Query.Filter attrEq = new Query.FilterPredicate("attribute", Query.FilterOperator.EQUAL, attr);
        Query query = new Query("Name").setFilter(attrEq);
        List<Entity> names = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        return Lists.transform(names, entityToTrainingName);
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
            if (orig != null) orig.add(1, name);
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

    private static void parseTrainingNamesForAttribute(NameAttribute attribute) {
        List<TrainingName> names = getTrainingNamesForAttribute(attribute);
        Map<String, Integer> fbegs = Maps.newHashMap();
        Map<String, Integer> fmids = Maps.newHashMap();
        Map<String, Integer> fends = Maps.newHashMap();
        Map<String, Integer> lbegs = Maps.newHashMap();
        Map<String, Integer> lmids = Maps.newHashMap();
        Map<String, Integer> lends = Maps.newHashMap();
        double ftotal = 0, fmeanTotal = 0, ltotal = 0, lmeanTotal = 0, i = 0;
        List<Integer> fsyls = Lists.newArrayList();
        List<Integer> lsyls = Lists.newArrayList();

        for (TrainingName name : names) {
            i++;
            Matcher firstMatcher = firstPat.matcher(name.getName());
            Matcher lastMatcher = lastPat.matcher(name.getName());
            String firstName = firstMatcher.find() ? firstMatcher.group() : "";
            String lastName = lastMatcher.find() ? lastMatcher.group() : "";
            int fsyl = getGroups(beg.matcher(firstName), fbegs) +
                       getGroups(mid.matcher(firstName), fmids) +
                       getGroups(end.matcher(firstName), fends);

            int lsyl = getGroups(beg.matcher(lastName), lbegs) +
                       getGroups(mid.matcher(lastName), lmids) +
                       getGroups(end.matcher(lastName), lends);
            ftotal += fsyl;
            ltotal += lsyl;
            fsyls.add(fsyl);
            lsyls.add(lsyl);
        }
        double fmean = ftotal / i;
        double lmean = ltotal / i;

        // second pass to get mean total
        for (Integer s : fsyls) {
            double var = s - fmean;
            fmeanTotal += var * var;
        }
        for (Integer s : lsyls) {
            double var = s - lmean;
            lmeanTotal += var * var;
        }
        WeightedNameData firsts = new WeightedNameData(new NameData(fbegs, fmids, fends, fmean, Math.sqrt(fmeanTotal /
                                                                                                          i)));
        WeightedNameData lasts = new WeightedNameData(new NameData(
            lbegs,
            lmids,
            lends,
            lmean,
            Math.sqrt(lmeanTotal / i)));
        attributeNameData.put(attribute, new AttributeNameData(firsts, lasts));
    }

    private static void parseNameData() {
        try {
            // generate the normal name stacks
            first = new WeightedNameData(parseNames(
                new BufferedReader(new FileReader("resources/names.txt")),
                origFirst));
            last = new WeightedNameData(parseNames(
                new BufferedReader(new FileReader("resources/lastnames.txt")),
                origLast));
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

    public static String makeName(NameAttribute attribute) {
        if (!init) {
            parseNameData();
        }

        if (attribute != null) {
            parseTrainingNamesForAttribute(attribute);
            return makeName(attributeNameData.get(attribute).first) +
                   " " +
                   makeName(attributeNameData.get(attribute).last);
        } else {
            return makeName(first) + " " + makeName(last);
        }
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
        if (!init) {
            parseNameData();
        }
        String name = format(origFirst.random()) + " " + format(origLast.random());
        return new TrainingName(name);
    }

    public static void train(TrainingName name) {
        datastore.put(name.entity());
    }
}
