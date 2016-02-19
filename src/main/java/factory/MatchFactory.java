package factory;

import model.Bookmaker;
import model.Match;

/**
 * Created by Дом on 18.02.2016.
 */
public class MatchFactory {
    public Match createMatch(String sportName, String date, String nameOfSide1, double coefficient1, String nameOfSide2, double coefficient2, int differenceInScore, double coefficient3) {
        Match match = new Match(sportName, date, nameOfSide1, coefficient1, nameOfSide2, coefficient2, differenceInScore, coefficient3);
        Bookmaker.matchList.add(match);//Почему только с большой буквы?

        return match;
    }
}
