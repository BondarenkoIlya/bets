package model;

import org.joda.money.Money;

import java.util.UUID;

/**
 * Created by Дом on 13.02.2016.
 */
abstract class BetsEntity {
    private UUID id = UUID.randomUUID();
    private double value;
    Match match;
    Customer customer;//Нужно говорить ставке что она пренадлежит определенному человеку ?
    private double possibleGain;
    private boolean result;

    public double getPossibleGain() {
        return possibleGain;
    }

    public void setPossibleGain(double possibleGain) {
        this.possibleGain = possibleGain;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
