package model;

import org.joda.money.Money;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {

    public Match createMatch(String sportName, String date, String nameOfSide1, double coefficient1, String nameOfSide2, double coefficient2, int differenceInScore, double coefficient3) {
        Match match = new Match(sportName, date, nameOfSide1, coefficient1, nameOfSide2, coefficient2, differenceInScore, coefficient3);
        Bookmaker.matchList.add(match);//Почему матч лист должен быть статиком??
    return match;
    }


    public void createBetByScore() {
        BetByScore betByScore = new BetByScore();
    }

    public BetByWinSide createBetByWinSide(Money value, Match match, String nameOfWinSide, boolean result) {
        BetByWinSide betByWinSide = new BetByWinSide(value,match, nameOfWinSide,result);
    return betByWinSide;
    }

    //результат ставки и подсчет тоже проводит сервис ?
}
