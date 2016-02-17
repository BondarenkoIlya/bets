package model;

import org.joda.money.Money;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {

    public Match createMatch(String sportName, String date, String nameOfSide1, double coefficient1, String nameOfSide2, double coefficient2, int differenceInScore, double coefficient3) {
        Match match = new Match(sportName, date, nameOfSide1, coefficient1, nameOfSide2, coefficient2, differenceInScore, coefficient3);
        Bookmaker.matchList.add(match);//Почему матч лист должен быть статиком?? я буду добавлять постоянно с одним и тем же именем новые обьекты , это нормально ?

    return match;
    }


    public BetByScore createBetByScore(double value, Match match, String nameOfWinSide) {
        BetByScore betByScore = new BetByScore();
        return betByScore;
    }

    public BetByWinSide createBetByWinSide(double value, Match match, String nameOfWinSide) {
        BetByWinSide betByWinSide = new BetByWinSide(value, match, nameOfWinSide);
        return betByWinSide;
    }

//сервис который пробежится по всем коллекциям ставочным и расставит все результаты и выйгрыши

}
