package com.epam.ilya.model;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.math.RoundingMode;

/**
 * Created by Дом on 14.02.2016.
 */
public class WinSideBet extends Bet {
    private String nameOfWinSide;
    private double coefficientOfWinSide;

    public WinSideBet(){

    }

    public WinSideBet(Money value, Match match, String nameOfWinSide, Customer customer) throws CashAccountBalanceExceptions {
        if(getCustomer().getPersonsPurse().balanceAvailabilityFor(value)){
            customer.getPersonsPurse().removeCash(value);
            this.setValue(value);
        }else{
            throw new CashAccountBalanceExceptions();
        }
        this.setMatch(match);
        this.setNameOfWinSide(nameOfWinSide);
        this.setCustomer(customer);
        this.fillFinalPossibleGain();
        this.setDate(DateTime.now());
    }


    public void fillFinalPossibleGain (){
        if (getNameOfWinSide()==getMatch().getNameOfSide1()) {
            this.setCoefficientOfWinSide(getMatch().getCoefficient1());
        }else if(getNameOfWinSide()==getMatch().getNameOfSide2()){
            this.setCoefficientOfWinSide(getMatch().getCoefficient2());
        }
        this.setPossibleGain(this.getValue().multipliedBy(this.getCoefficientOfWinSide(),RoundingMode.DOWN));
    }


    public boolean setFinalResult() {
        if (getMatch().getNameOfWinSide()==this.getNameOfWinSide()) {
            this.setResult(true);
            return true;

        }else {
            this.setResult(false);
            return false;
        }


    }

    public String getNameOfWinSide() {
        return nameOfWinSide;
    }

    public void setNameOfWinSide(String nameOfWinSide) {
        this.nameOfWinSide = nameOfWinSide;
    }

    public double getCoefficientOfWinSide() {
        return coefficientOfWinSide;
    }

    public void setCoefficientOfWinSide(double coefficientOfWinSide) {
        this.coefficientOfWinSide = coefficientOfWinSide;
    }
}
