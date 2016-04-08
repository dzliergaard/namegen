package com.rptools.name;

import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.rptools.s3.NameFileParser;

@Component
@CommonsLog
public class NameGen {
    private static final Joiner JOINER = Joiner.on(" ");
    private static final String PARSED_TIME = "Name data parsed in %d milliseconds.";
    private final WeightedNames first;
    private final WeightedNames last;

    @Autowired
    public NameGen(NameFileParser nameFileParser) {
        // Stopwatch timer = Stopwatch.createStarted();
        first = nameFileParser.parseFile("names.txt");
        last = nameFileParser.parseFile("lastnames.txt");
        // timer.stop();
        // log.info(String.format(PARSED_TIME, timer.elapsed(TimeUnit.MILLISECONDS)));
    }

    public String makeName() {
        return JOINER.join(makeName(first), makeName(last));
    }

    private String makeName(WeightedNames weightedNames) {
        String name = weightedNames.beg.random();
        int syl = weightedNames.syllables();
        if (--syl == 0) {
            return capitalize(name);
        }
        if (--syl == 0) {
            return capitalize(name + weightedNames.end.random());
        }
        while (syl-- > 0) {
            name += weightedNames.mid.random();
        }
        name += weightedNames.end.random();
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
        // datastore.put(name.entity());
    }

    public TrainingName getTrainingName() {
        return new TrainingName(makeName());
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
