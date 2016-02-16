package model;

/**
 * Created by Дом on 09.02.2016.
 */
public class Match {

    private String sportName;
    private String date;
    private String nameOfSide1;
    private double coefficient1;
    private String nameOfSide2;
    private double coefficient2;
    private String nameOfWinSide;

    private int differenceInScore;
    private double coefficient3;

    public Match() {

    }

    public Match(String sportName, String date, String nameOfSide1, double coefficient1, String nameOfSide2, double coefficient2, int differenceInScore, double coefficient3) {
        this.setSportName(sportName);
        this.setDate(date);
        this.setNameOfSide1(nameOfSide1);
        this.setCoefficient1(coefficient1);
        this.setNameOfSide2(nameOfSide2);
        this.setCoefficient2(coefficient2);
        this.setDifferenceInScore(differenceInScore);
        this.setCoefficient3(coefficient3);
    }

    public void setResults(String nameOfWinSide,int differenceInScore){
        this.nameOfWinSide=nameOfWinSide;
        this.differenceInScore=differenceInScore;
    }

    public String getNameOfWinSide() {
        return nameOfWinSide;
    }


    public double getCoefficient3() {
        return coefficient3;
    }

    public void setCoefficient3(double coefficient3) {
        this.coefficient3 = coefficient3;
    }

    public int getDifferenceInScore() {
        return differenceInScore;
    }

    public void setDifferenceInScore(int differenceInScore) {
        this.differenceInScore = differenceInScore;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameOfSide1() {
        return nameOfSide1;
    }

    public void setNameOfSide1(String nameOfSide1) {
        this.nameOfSide1 = nameOfSide1;
    }

    public double getCoefficient1() {
        return coefficient1;
    }

    public void setCoefficient1(double coefficient1) {
        this.coefficient1 = coefficient1;
    }

    public String getNameOfSide2() {
        return nameOfSide2;
    }

    public void setNameOfSide2(String nameOfSide2) {
        this.nameOfSide2 = nameOfSide2;
    }

    public double getCoefficient2() {
        return coefficient2;
    }

    public void setCoefficient2(double coefficient2) {
        this.coefficient2 = coefficient2;
    }


}
