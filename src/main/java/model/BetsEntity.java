package model;

import org.joda.money.Money;

import java.util.UUID;

/**
 * Created by Дом on 13.02.2016.
 */
abstract class BetsEntity {
    private UUID id = UUID.randomUUID();
    private Money value;
    Match match;
    Customer customer;
    //получит если выйграет
    private boolean result;



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
}
