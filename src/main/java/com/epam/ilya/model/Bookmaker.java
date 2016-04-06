package com.epam.ilya.model;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Bookmaker extends Person {
    public static final Bookmaker bookmaker = new Bookmaker("Алексей Анатольевич");

    private Bookmaker() {

    }

    public Bookmaker(String name) {
        this.setName(name);
    }





}
