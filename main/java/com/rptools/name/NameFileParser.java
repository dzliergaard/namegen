package com.rptools.name;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rptools.util.Logger;

@Component
public class NameFileParser {
    private final Logger log = Logger.getLogger(NameFileParser.class);
    private Pattern beg = Pattern.compile("^([^AEIOUY]*[AEIOUY]+[^AEIOUY]*(?:E$)?)?");
    private Pattern mid = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]+)[AEIOUY]");
    private Pattern end = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]*[E]?)$");
    private Pattern namePat = Pattern.compile("[\\w]+");

    public WeightedNames parseNameFile(String fileName) {
        try {
            return parseNames(new BufferedReader(new FileReader(fileName)));
        } catch (IOException e) {
            log.warning("Error parsing name data: %e", e.getMessage());
        }
        return new WeightedNames();
    }

    /**
     * Parses the name file in BufferedReader into the WeightedNames data for random generation
     * 
     * @throws IOException
     */
    private WeightedNames parseNames(BufferedReader br) throws IOException {
        String str, nameDataStr = "";
        while ((str = br.readLine()) != null) {
            nameDataStr += str.trim();
        }
        br.close();

        Matcher nameMatcher = namePat.matcher(nameDataStr);
        Map<String, Integer> begs = Maps.newHashMap();
        Map<String, Integer> mids = Maps.newHashMap();
        Map<String, Integer> ends = Maps.newHashMap();

        SummaryStatistics stats = new SummaryStatistics();
        // double total = 0, meanTotal = 0, i = 0;
        List<Integer> syls = Lists.newArrayList();
        while (nameMatcher.find()) {
            String name = nameMatcher.group();
            int syl = getGroups(beg.matcher(name), begs);
            syl += getGroups(mid.matcher(name), mids);
            syl += getGroups(end.matcher(name), ends);
            syls.add(syl);
            stats.addValue(syl);
        }
        return new WeightedNames(new Names(begs, mids, ends), stats);
    }

    private int getGroups(Matcher matcher, Map<String, Integer> groups) {
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
}
