package model;

import org.joda.money.Money;

/**
 * Created by Дом on 14.02.2016.
 */
public class BetByWinSide extends BetsEntity {
    private String nameOfWinSide;
    private double coefficientOfWinSide;

    public BetByWinSide(){

    }

    public BetByWinSide(double value,Match match, String nameOfWinSide){//как реализовать что бы можно было выбирать что ставка делается на определенный матч ?
        this.setValue(value);
        this.match=match;
        this.setNameOfWinSide(nameOfWinSide);
    }


    public void setFinalPossibleGain (){
        if (getNameOfWinSide()==match.getNameOfSide1()) {
            this.coefficientOfWinSide=match.getCoefficient1();
        }else if(getNameOfWinSide()==match.getNameOfSide2()){
            this.coefficientOfWinSide=match.getCoefficient2();
        }
        this.setPossibleGain(this.getValue()*this.coefficientOfWinSide);

    }


    public void setFinalResult() {
        if (match.getNameOfWinSide()==this.getNameOfWinSide()) {
            this.setResult(true);
            System.out.println("Поздравляю,вы выйграли!");
        }else {
            this.setResult(false);
            System.out.println("Извините, вы проиграли!");
        }


    }

    public String getNameOfWinSide() {
        return nameOfWinSide;
    }

    public void setNameOfWinSide(String nameOfWinSide) {
        this.nameOfWinSide = nameOfWinSide;
    }
}
