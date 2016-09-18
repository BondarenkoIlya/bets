package com.epam.ilya.model;

/**
 * Condition is an aspect of the match which made the rate.
 * Contain 3 field: condition name(aspect of the match), result - say condition name become
 * true or false and coefficient that show how much more money you will get in case of
 * ges result
 *
 * @author Bondarenko Ilya
 */

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

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 37 + getId();
        hash = hash * 37 + conditionsName.hashCode();
        hash = (int) (hash * 37 + coefficient);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Condition)) {
            return false;
        } else {
            Condition condition = (Condition) obj;
            return this.hashCode() == condition.hashCode();
        }
    }
}
