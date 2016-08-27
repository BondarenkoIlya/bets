package com.epam.ilya.model;

public class Condition extends BaseEntity{
    //private Match match;
    private String conditionsName;
    private double coefficient;
    private boolean result;

    public Condition() {
    }

    public Condition(double coefficient, String conditionName) {
        this.coefficient = coefficient;
        this.conditionsName = conditionName;
    }

    /*public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }*/

    public String getConditionsName() {
        return conditionsName;
    }

    public void setConditionsName(String conditionsName) {
        this.conditionsName = conditionsName;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "coefficient=" + coefficient +
                ", conditionsName='" + conditionsName + '\'' +
                '}';
    }
}
