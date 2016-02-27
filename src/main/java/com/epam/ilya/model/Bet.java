package com.epam.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Дом on 13.02.2016.
 */
public abstract class Bet {
    private UUID id = UUID.randomUUID();
    private DateTime date;
    private Money value;
    private Match match;
    private Customer customer;//Нужно говорить ставке что она пренадлежит определенному человеку ?
    private Money possibleGain;
    private boolean result;

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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public Money getValue() {
        return value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "customer=" + customer +
                ", match=" + match +
                ", result=" + result +
                ", value=" + value +
                '}';
    }
    public static final Comparator<Bet> VALUE_ORDER = new ValueComparator();

    private static class ValueComparator implements Comparator<Bet> {

        @Override
        public int compare(Bet o1, Bet o2) {
            Money val1 = o1.getValue();
            Money val2 = o2.getValue();
            return val1.compareTo(val2);
        }
    }
}
