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

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.google.gson.Gson;
import com.rptools.name.Names;
import com.rptools.util.FileUtils;
import com.rptools.util.WeightedTrieBuilder;

/**
 * Parses first and last name data files from S3. Creates {@link SummaryStatistics} object for {@link Names} to
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
    private static final Pattern groupPat = Pattern.compile("[^AEIOUY]+|[AEIOUY]+");
    private static final Pattern endPat = Pattern.compile("(?:[^AEIOUY]+|[AEIOUY]+)$");
    private static final Pattern namePat = Pattern.compile("\\w+:\\d+");

    @Autowired
    public NameFileParser(AmazonS3 s3, FileUtils fileUtils, Gson gson) {
        super(s3, "dzlier-resources", fileUtils, gson);
    }

    @Override
    protected Names parseFileData(String data) {
        WeightedTrieBuilder<String> firstTrieBuilder = new WeightedTrieBuilder<>();
        WeightedTrieBuilder<String> middleTrieBuilder = new WeightedTrieBuilder<>();
        WeightedTrieBuilder<String> endTrieBuilder = new WeightedTrieBuilder<>();

        Matcher nameMatcher = namePat.matcher(data);
        SummaryStatistics stats = new SummaryStatistics();

        while (nameMatcher.find()) {
            String[] nameFreq = nameMatcher.group().split(":");
            String name = nameFreq[0];
            Integer weight = Integer.valueOf(nameFreq[1]);
            Matcher matcher = groupPat.matcher(name);
            String parent = null;
            String group = "";
            boolean firstChild = true;
            int groups = 0;

            // add first syllable as child of first group trie
            while (matcher.find()) {
                if (matcher.hitEnd()) {
                    break;
                }
                group = matcher.group();
                // if no ancestors, then this is first group: add to firstTrieBuilder
                if (parent == null) {
                    firstTrieBuilder.addChild(group, weight);
                    parent = group;
                } else {
                    // if second group, add as child to firstTrieBuilder
                    if (firstChild) {
                        firstTrieBuilder.addChild(group, weight, parent);
                        firstChild = false;
                    }
                    middleTrieBuilder.addChild(group, weight, parent);
                    parent = group;
                }
                groups++;
            }

            matcher = endPat.matcher(name);
            if (matcher.find()) {
                endTrieBuilder.addChild(matcher.group(), weight, group);
                groups++;
            }

            stats.addValue(groups);
        }

        return new Names(firstTrieBuilder.build(), middleTrieBuilder.build(), endTrieBuilder.build(), stats);
    }
}
