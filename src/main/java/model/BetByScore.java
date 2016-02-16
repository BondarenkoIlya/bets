package model;

/**
 * Created by Дом on 14.02.2016.
 */
public class BetByScore extends BetsEntity {
    private int differenceInScore;




    public int getDifferenceInScore() {
        return differenceInScore;
    }

    public void setDifferenceInScore(int differenceInScore) {
        this.differenceInScore = differenceInScore;
    }
}
