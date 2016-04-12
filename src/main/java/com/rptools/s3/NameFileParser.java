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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.google.gson.Gson;
import com.rptools.name.Names;
import com.rptools.name.WeightedNames;
import com.rptools.util.FileUtils;
import com.rptools.util.WeightedTrieBuilder;
import com.rptools.util.WeightedTrieBuilder.TrieNodeBuilder;

/**
 * Parses first and last name data files from S3. Creates {@link SummaryStatistics} object for {@link WeightedNames} to
 * choose from later.
 * 
 * Names are broken down as follows:
 * 1) They are split into groups of 1+ vowels or 1+ consonants: MEPHISTOPHOLES = M, E, PH, I, ST, O, PH, O, L, E, S
 * 2) The first group goes into a special "beginning" 2-level TRIE, pointing to all its possible children
 * 3) All other groups go into second 2-level TRIE, with each group pointing to its possible child groups (any
 * groups that follow and how frequently).
 * 
 * End result:
 * firstTrie:
 * M -> E
 * 
 * middleTrie:
 * E -> PH, S
 * PH -> I, O
 * I -> ST
 * ST -> O
 * O -> PH, L
 * L -> E
 * S ->
 */
@Component
@CommonsLog
public class NameFileParser extends FileParser<Names> {
    private static final Pattern groupPat = Pattern.compile("[^AEIOUY]+|[AEIOUY]+(?!$)");
    private static final Pattern endPat = Pattern.compile("([^AEIOUY]+|[AEIOUY]+)([^AEIOUY]+|[AEIOUY]+)$");
    private static final Pattern namePat = Pattern.compile("\\w+:\\d+");

    @Autowired
    public NameFileParser(AmazonS3 s3, FileUtils fileUtils, Gson gson) {
        super(s3, "dzlier-resources", fileUtils, gson);
    }

    @Override
    protected Names parseFileData(String data) {
        WeightedTrieBuilder<String> firstTrieBuilder = new WeightedTrieBuilder<>("");
        WeightedTrieBuilder<String> middleTrieBuilder = new WeightedTrieBuilder<>("");
        WeightedTrieBuilder<String> endTrieBuilder = new WeightedTrieBuilder<>("");

        Matcher nameMatcher = namePat.matcher(data);
        SummaryStatistics stats = new SummaryStatistics();
        double groupTotal = StringUtils.countMatches(data, ",") + 1;
        double lastPercent = .1;
        double i = 0;

        while (nameMatcher.find()) {
            i++;
            String[] nameFreq = nameMatcher.group().split(":");
            String name = nameFreq[0];
            Integer frequency = Integer.valueOf(nameFreq[1]);
            Matcher matcher = groupPat.matcher(name);
            int groups = 0;

            // add first syllable as child of first group trie
            if (matcher.find()) {
                String group = matcher.group();
                TrieNodeBuilder<String> parent = firstTrieBuilder.addChild(group, frequency);
                groups++;

                while (matcher.find()) {
                    group = matcher.group();
                    parent.addChild(group, frequency);
                    parent = middleTrieBuilder.addChild(group, frequency);
                    groups++;
                }

            }

            matcher = endPat.matcher(name);
            if (matcher.find()) {
                String secondToLast = matcher.group(1);
                String last = matcher.group(2);
                endTrieBuilder.addChild(secondToLast, frequency).addChild(last, frequency);
            }

            stats.addValue(groups);
            if (i / groupTotal > lastPercent) {
                lastPercent += .1;
                log.info(String.format("Parsing names, %2.1f percent complete", (i / groupTotal) * 100.));
            }
        }

        return new Names(firstTrieBuilder.build(), middleTrieBuilder.build(), endTrieBuilder.build(), stats);
    }
}
