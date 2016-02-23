package com.epam.ilya.model;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {

    public void completeBet(Customer customer){
            for (Bet bet : customer.getBets()) {
                if (bet instanceof WinSideBet) {
                    WinSideBet winSideBet = (WinSideBet) bet;
                    ((WinSideBet) bet).fillFinalResult();
                }else if (bet instanceof ScoreBet) {
                    ScoreBet scoreBet = (ScoreBet) bet;
                    ((WinSideBet) bet).fillFinalResult();
                }
            }
    }
}
