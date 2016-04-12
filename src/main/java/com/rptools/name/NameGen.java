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

package com.rptools.name;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.rptools.s3.NameFileParser;

/**
 * Helper component that generates names from the {@link Names} object returned by {@link NameFileParser}
 */
@Component
@CommonsLog
public class NameGen {
    private static final Joiner JOINER = Joiner.on(" ");
    private static final String PARSED_TIME = "Name data parsed in %d milliseconds.";
    private final Names first;
    private final Names last;

    @Autowired
    public NameGen(NameFileParser nameFileParser) {
        Stopwatch timer = Stopwatch.createStarted();
        first = nameFileParser.parseFile("names", Names.class);
        last = nameFileParser.parseFile("lastNames", Names.class);
        timer.stop();
        log.info(String.format(PARSED_TIME, timer.elapsed(TimeUnit.MILLISECONDS)));
    }

    public String makeName() {
        return JOINER.join(makeName(first), makeName(last));
    }

    private String makeName(Names names) {
        String name = names.beg.random();
        String group = name;
        int groups = names.groups() - 1;
        group = names.beg.random(group).orElse("");
        name += group;

        if (--groups == 0 || !name.matches(".*[AEIOUY]+.*")) {
            return capitalize(name);
        }

        while (groups-- > 1 || !name.matches(".*[AEIOUY]+.*")) {
            group = names.mid.random(group).orElse("");
            name += group;
        }

        name += names.end.random(group).orElse("");

        return capitalize(name);
    }

    public List<Name> generateNames(int numNames) {
        List<Name> names = Lists.newArrayList();
        while (numNames-- > 0) {
            Name name = new Name(makeName());
            names.add(name);
        }
        return names;
    }

    public void train(TrainingName name) {
    }

    public TrainingName getTrainingName() {
        return new TrainingName(makeName());
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
