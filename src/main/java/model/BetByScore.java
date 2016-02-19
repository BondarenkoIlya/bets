package model;

/**
 * Created by Дом on 14.02.2016.
 */
public class BetByScore extends BetEntity {
    private int differenceInScore;
    private double coefficientOfWinSide;


    public void fillFinalPossibleGain (){
        this.coefficientOfWinSide=match.getCoefficient3();
        this.setPossibleGain(this.getValue()*this.coefficientOfWinSide);
    }

    public void fillFinalResult() {
        if (match.getDifferenceInScore()==this.differenceInScore) {
            this.setResult(true);
            System.out.println("Поздравляю,вы выйграли "+this.getPossibleGain());
        }else {
            this.setResult(false);
            System.out.println("Извините, вы проиграли "+this.getValue());
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
