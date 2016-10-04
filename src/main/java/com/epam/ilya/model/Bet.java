package com.epam.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Bet class describes an agreement between two parties that a money
 * will be paid by the loser to the winner (the winner being the one who correctly
 * forecast the outcome of an event).
 *
 * @author Bondarenko Ilya
 */

public class Bet extends BaseEntity {
    private DateTime date;
    private Money value;
    private Customer customer;
    private Money possibleGain;
    private List<Condition> conditions = new ArrayList<>();
    private double finalCoefficient;
    private Boolean finalResult;

    public Bet() {
        this.date = DateTime.now();
    }

    public Bet(Money value) {
        this.date = DateTime.now();
        this.value = value;
    }

    public Bet(Money value, Customer customer) {
        this.date = DateTime.now();
        this.value = value;
        this.customer = customer;

    }

    public void calculateFinalCoefficient() {
        this.finalCoefficient = 1;
        for (Condition c : conditions) {
            this.finalCoefficient = this.finalCoefficient * c.getCoefficient();
        }
    }

    public void calculatePossibleGain() {
        this.possibleGain = value.multipliedBy(finalCoefficient, RoundingMode.HALF_UP);
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Money getPossibleGain() {
        return possibleGain;
    }

    public void setPossibleGain(Money possibleGain) {
        this.possibleGain = possibleGain;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Boolean getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(Boolean finalResult) {
        this.finalResult = finalResult;
    }

    public double getFinalCoefficient() {
        return finalCoefficient;
    }

    public void setFinalCoefficient(double finalCoefficient) {
        this.finalCoefficient = finalCoefficient;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "value=" + value +
                ", date=" + date +
                ", possibleGain=" + possibleGain +
                ", finalCoefficient=" + finalCoefficient +
                ", finalResult=" + finalResult +
                '}';
    }
}
