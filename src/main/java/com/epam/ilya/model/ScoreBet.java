package com.epam.ilya.model;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.math.RoundingMode;

/**
 * Created by Дом on 14.02.2016.
 */
public class ScoreBet extends Bet {
    private int differenceInScore;
    private double coefficientOfWinSide;

    public ScoreBet(){

    }

    public ScoreBet(Money value, Match match, int differenceInScore, Customer customer) throws CashAccountBalanceExceptions {
        System.out.println(customer);

        this.setMatch(match);
        this.setDifferenceInScore(differenceInScore);
        this.setCustomer(customer);
        this.fillFinalPossibleGain();
        this.setDate(DateTime.now());
        if(getCustomer().getPersonsPurse().balanceAvailabilityFor(value)){
            customer.getPersonsPurse().removeCash(value);
            this.setValue(value);
        }else{
            throw new CashAccountBalanceExceptions();
        }

    }




    public void fillFinalPossibleGain (){
        this.setCoefficientOfWinSide(getMatch().getCoefficient3());
        System.out.println(getValue());
        System.out.println(getCoefficientOfWinSide());
        this.setPossibleGain(getValue().multipliedBy(this.getCoefficientOfWinSide(),RoundingMode.valueOf(1)));
    }

    public boolean setFinalResult() {
        if (getMatch().getDifferenceInScore()==this.differenceInScore) {
            this.setResult(true);
            return true;
        }else {
            this.setResult(false);
            return false;
        }
    }

    public double getCoefficientOfWinSide() {
        return coefficientOfWinSide;
    }

    public void setCoefficientOfWinSide(double coefficientOfWinSide) {
        this.coefficientOfWinSide = coefficientOfWinSide;
    }

    public int getDifferenceInScore() {
        return differenceInScore;
    }

    public void setDifferenceInScore(int differenceInScore) {
        this.differenceInScore = differenceInScore;
    }
}
