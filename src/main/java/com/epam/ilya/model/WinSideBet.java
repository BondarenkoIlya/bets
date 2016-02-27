package com.epam.ilya.model;

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

    public WinSideBet(Money value, Match match, String nameOfWinSide, Customer customer){
        this.setValue(value);
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


    public void setFinalResult() {
        if (getMatch().getNameOfWinSide()==this.getNameOfWinSide()) {
            this.setResult(true);
        }else {
            this.setResult(false);
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
