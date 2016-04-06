package com.epam.ilya.factory;

import com.epam.ilya.model.Match;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;

public class MatchFactory {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(MatchFactory.class));

    public Match createMatch(String sportName,String leagueName, DateTime date, String nameOfSide1, String nameOfSide2) {
        Match match = new Match(sportName,leagueName, date, nameOfSide1, nameOfSide2);
        log.info("Create new match " + match);
        return match;
    }
}
