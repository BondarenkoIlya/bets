package model;

import org.joda.money.Money;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {

    public void completeBet(){
        for (Customer customer :Bookmaker.customerList ){
            for (Object bet : customer.bets) {
                if (bet instanceof BetByWinSide) {
                    BetByWinSide betByWinSide = (BetByWinSide) bet;
                    ((BetByWinSide) bet).fillFinalPossibleGain();
                    ((BetByWinSide) bet).fillFinalResult();
                }else if (bet instanceof BetByScore) {
                    BetByScore betByScore = (BetByScore) bet;
                    ((BetByWinSide) bet).fillFinalPossibleGain();
                    ((BetByWinSide) bet).fillFinalResult();
                }
            }
        }
    }
}
