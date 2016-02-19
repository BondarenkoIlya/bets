package factory;

import model.BetByScore;
import model.BetByWinSide;
import model.Customer;
import model.Match;

/**
 * Created by Дом on 18.02.2016.
 */
public class BetFactory {
    public BetByScore createBetByScore(double value, Match match, String nameOfWinSide) {
        BetByScore betByScore = new BetByScore();
        return betByScore;
    }

    public BetByWinSide createBetByWinSide(double value, Match match, String nameOfWinSide,Customer customer) {
        BetByWinSide betByWinSide = new BetByWinSide(value, match, nameOfWinSide,customer);

        return betByWinSide;
    }
}
