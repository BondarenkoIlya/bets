package com.epam.ilya.model;

import org.joda.money.Money;

import java.math.RoundingMode;

/**
 * Created by Дом on 14.02.2016.
 */
public class ScoreBet extends Bet {
    private int differenceInScore;
    private double coefficientOfWinSide;

    public ScoreBet(){

    }

    public ScoreBet(Money value, Match match, int differenceInScore, Customer customer){
        this.setValue(value);
        this.setMatch(match);
        this.setDifferenceInScore(differenceInScore);
        this.setCustomer(customer);
        this.fillFinalPossibleGain();
    }


    public void fillFinalPossibleGain (){
        this.setCoefficientOfWinSide(getMatch().getCoefficient3());
        this.setPossibleGain(getValue().multipliedBy(this.getCoefficientOfWinSide(), RoundingMode.DOWN));
    }

    public void fillFinalResult() {
        if (getMatch().getDifferenceInScore()==this.differenceInScore) {
            this.setResult(true);
            System.out.println("Поздравляю, " +this.getCustomer().getName()+ ", вы выйграли "+this.getPossibleGain());
        }else {
            this.setResult(false);
            System.out.println("Извините," +this.getCustomer().getName()+ ", вы проиграли "+this.getValue());
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
