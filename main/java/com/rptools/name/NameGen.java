package com.rptools.name;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.rptools.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NameGen {
    private static final Joiner JOINER = Joiner.on(" ");
    private final WeightedNames first;
    private final WeightedNames last;

    @Autowired
    public NameGen(NameFileParser nameFileParser) {
        Stopwatch timer = Stopwatch.createStarted();
        first = nameFileParser.parseNameFile("resources/names.txt");
        last = nameFileParser.parseNameFile("resources/lastnames.txt");
        timer.stop();
        Logger.getLogger(NameGen.class).info(
                "Name data parsed in %d milliseconds.",
                timer.elapsed(TimeUnit.MILLISECONDS));
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

    private String capitalize(String name){
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
