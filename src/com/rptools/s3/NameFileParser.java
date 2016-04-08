/*
 * RPToolkit - Tools to assist Role-Playing Game masters and players
 * Copyright (C) 2016 Dane Zeke Liergaard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.rptools.s3;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rptools.name.Names;
import com.rptools.name.WeightedNames;

/**
 * Parses first and last name data files from S3. Creates {@link SummaryStatistics} object for {@link WeightedNames} to
 * choose from later.
 */
@Component
@CommonsLog
public class NameFileParser extends FileParser<WeightedNames> {
    private static final Pattern beg = Pattern.compile("^([^AEIOUY]*[AEIOUY]+[^AEIOUY]*(?:E$)?)?");
    private static final Pattern mid = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]+)[AEIOUY]");
    private static final Pattern end = Pattern.compile("[AEIOUY]+[^AEIOUY]+([AEIOUY]+[^AEIOUY]*[E]?)$");
    private static final Pattern namePat = Pattern.compile("[\\w]+");

    @Autowired
    public NameFileParser(AmazonS3 s3) {
        super(s3, "dzlier-resources");
    }

    /**
     * Parses the name file in BufferedReader into the WeightedNames data for random generation
     */
    @Override
    protected WeightedNames parseFileData(String data) {
        Matcher nameMatcher = namePat.matcher(data);
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
