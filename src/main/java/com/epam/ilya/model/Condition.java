package com.epam.ilya.model;

public class Condition {
    private Match match;
    private String conditionName;
    private double coefficient;
    private boolean result;

    public Condition() {
    }

    public Condition(double coefficient, String conditionName) {
        this.coefficient = coefficient;
        this.conditionName = conditionName;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
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
                ", conditionName='" + conditionName + '\'' +
                '}';
    }
}
