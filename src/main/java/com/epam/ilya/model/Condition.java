package com.epam.ilya.model;

public class Condition extends BaseEntity {
    private String conditionsName;
    private double coefficient;
    private Boolean result;

    public Condition() {
    }

    public Condition(double coefficient, String conditionName) {
        this.coefficient = coefficient;
        this.conditionsName = conditionName;
    }

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

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionsName='" + conditionsName + '\'' +
                ", coefficient=" + coefficient +
                ", result=" + result +
                '}';
    }
}
