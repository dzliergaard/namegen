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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.dzlier.weight.WeightedTrie;
import com.google.gson.Gson;
import com.rptools.io.FileUtils;
import com.rptools.name.Names;

/**
 * Parses first and last name data files from S3. Creates {@link SummaryStatistics} object for {@link Names} to
 * choose from later.
 * 
 * Names are broken down as follows:
 * 1) They are split into groups of 1+ vowels or 1+ consonants
 * 2) The first group goes into a special "beginning" 3-level TRIE, pointing to all its possible children and
 * grandchildren.
 * 3) All other non-ending groups go into second 3-level TRIE, with each group pointing to its possible children and
 * grandchildren.
 * 4) The end group is put into a 3-level TRIE, with its 2 preceding groups leading to it
 * 
 * This creates believable-sounding names as the ones it create tend to follow groups that they follow in the most
 * popular real-life names.
 * 
 * Example: MEPHISTOPHOLES = M.E.PH.I.ST.O.PH.O.L.E.S
 * End result:
 * firstTrie:
 * M -> E -> PH
 * 
 * middleTrie:
 * E -> (PH -> I), (S)
 * PH -> (I -> ST), (O -> L)
 * I -> (ST -> O)
 * ST -> (O -> PH)
 * O -> (PH -> O), (L -> E)
 * 
 * endTrie:
 * L -> (E -> S)
 */
@Component
@CommonsLog
public class NameFileParser extends FileParser<Names> {
    private static final Pattern groupPat = Pattern.compile("[^QAEIOUY]+|Q?[AEIOUY]+");
    private static final Pattern namePat = Pattern.compile("\\w+:\\d+");

    @Autowired
    public NameFileParser(FileUtils fileUtils, Gson gson) {
        super(fileUtils, gson);
    }

    @Override
    protected Names parseFileData(String data) {
        WeightedTrie<String> firstTrieBuilder = new WeightedTrie<>();
        WeightedTrie<String> middleTrieBuilder = new WeightedTrie<>();
        WeightedTrie<String> endTrieBuilder = new WeightedTrie<>();

        Matcher nameMatcher = namePat.matcher(data);
        SummaryStatistics stats = new SummaryStatistics();

        while (nameMatcher.find()) {
            String[] nameFreq = nameMatcher.group().split(":");
            String name = nameFreq[0];
            Double weight = Double.valueOf(nameFreq[1]);
            String[] groups = groupifyName(name);
            int numGroups = groups.length;
            if (numGroups < 2) {
                continue;
            } else if (numGroups <= 3) {
                firstTrieBuilder.addChain(weight, groups);
            } else {
                firstTrieBuilder.addChain(weight, Arrays.copyOfRange(groups, 0, 3));
                int i = 0;
                while (i++ < numGroups - 2) {
                    middleTrieBuilder.addChain(weight, Arrays.copyOfRange(groups, i, i + 3));
                }
                endTrieBuilder.addChain(weight, Arrays.copyOfRange(groups, numGroups - 3, numGroups));
            }
            stats.addValue(numGroups);
        }

        return new Names(firstTrieBuilder, middleTrieBuilder, endTrieBuilder, stats);
    }

    /**
     * Break {@param name} down into groups of 1+ consonants or 1+ vowels (give or take a Q
     * 
     * @return name groups as array
     */
    private String[] groupifyName(String name) {
        int o = 0;
        Matcher groupMatcher = groupPat.matcher(name);
        while (groupMatcher.find()) {
            o++;
        }
        String[] result = new String[o];
        groupMatcher.reset();
        int i = 0;
        while (groupMatcher.find()) {
            result[i++] = groupMatcher.group();
        }
        return result;
    }
}
