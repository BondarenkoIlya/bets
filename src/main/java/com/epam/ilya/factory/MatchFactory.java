package com.epam.ilya.factory;

import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Match;
import org.joda.time.DateTime;

/**
 * Created by Дом on 18.02.2016.
 */
public class MatchFactory {
    public Match createMatch(String sportName, DateTime date, String nameOfSide1, double coefficient1, String nameOfSide2, double coefficient2, double coefficient3) {
        Match match = new Match(sportName, date, nameOfSide1, coefficient1, nameOfSide2, coefficient2, coefficient3);

        return match;
    }
}
