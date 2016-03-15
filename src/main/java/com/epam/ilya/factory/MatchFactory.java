package com.epam.ilya.factory;

import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Match;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * Created by Дом on 18.02.2016.
 */
public class MatchFactory {
    private static List<String> footballTeams = asList("Leicester City","Tottenham Hotspur","Arsenal","Manchester City","West Ham United","Manchester United","Southampton","Stoke City","Liverpool","Watford");

    public MatchFactory() {
    }

    public Match createMatch() {
        String sportName = "Football";
        DateTime date = new DateTime(2016, (int) ((Math.random() * 9) + 4), (int) (Math.random() * 30), 19, 0, 0);

        String nameOfSide1 = null;
        String nameOfSide2 = null;
        for (int i = 0; i < 10; i++) {
            nameOfSide1 = footballTeams.get((int) ((Math.random() * 10)));
            nameOfSide2 = footballTeams.get((int) ((Math.random() * 10)));
            if (!Objects.equals(nameOfSide1, nameOfSide2)) break;
        }
        double coefficient2 = ((Math.random() * 5)+1);
        double coefficient1 = ((Math.random() * 5)+1);
        double coefficient3 = ((Math.random() * 5)+1);
        return new Match(sportName, date, nameOfSide1, coefficient1, nameOfSide2, coefficient2, coefficient3);
    }

    public static List<String> getFootballTeams() {
        return footballTeams;
    }
}
