package com.epam.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Bet class describe  an agreement between two parties that a money
 * will be paid by the loser to the winner (the winner being the one who correctly
 * forecast the outcome of an event).
 * @author Bondarenko Ilya
 */

public class Bet extends BaseEntity {
    private DateTime date;
    private Money value;
    private Customer customer;
    private Money possibleGain;
    private List<Condition> conditions = new ArrayList<Condition>();
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

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
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

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + getId();
        hash = hash*37 + date.hashCode();
        hash = hash*37 + value.hashCode();
        hash = hash*37 + customer.hashCode();
        hash = hash*37 + possibleGain.hashCode();
        hash = hash*37 + conditions.hashCode();
        hash = (int) (hash*37 + finalCoefficient);
        if(finalResult){
            hash = hash*37 + 1;
        }else {
            hash = hash*37;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if (!(obj instanceof Bet)){
            return false;
        }else {
            Bet bet = (Bet) obj;
            return this.hashCode()==bet.hashCode();
        }
    }
}
