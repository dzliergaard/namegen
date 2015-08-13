package com.rptools.name;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.rptools.util.Logger;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
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
            return WordUtils.capitalize(name);
        }
        if (--syl == 0) {
            return WordUtils.capitalize(name + weightedNames.end.random());
        }
        while (syl-- > 0) {
            name += weightedNames.mid.random();
        }
        name += weightedNames.end.random();
        return WordUtils.capitalize(name);
    }
}
