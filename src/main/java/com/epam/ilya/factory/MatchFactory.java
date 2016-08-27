package com.epam.ilya.factory;

import com.epam.ilya.model.Match;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchFactory {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(MatchFactory.class));

    public Match createMatch(String sportsName,String leaguesName, DateTime date, String firstSidesName, String secondSidesName) {
        Match match = new Match(sportsName,leaguesName, date, firstSidesName, secondSidesName);
        log.info("Create new match " + match);
        return match;
    }
}
