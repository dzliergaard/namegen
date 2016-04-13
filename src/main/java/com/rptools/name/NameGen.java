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

import org.apache.commons.lang3.text.WordUtils;
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

    public List<Name> generateNames(int numNames) {
        List<Name> names = Lists.newArrayList();
        while (numNames-- > 0) {
            Name name = new Name(makeName());
            names.add(name);
        }
        return names;
    }

    public TrainingName getTrainingName() {
        return new TrainingName(makeName());
    }

    public void train(TrainingName name) {
    }

    private String makeName() {
        return WordUtils.capitalize(JOINER.join(first.makeName(), last.makeName()));
    }
}
