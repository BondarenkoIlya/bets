package com.epam.ilya.model;

/**
 * Created by Дом on 09.02.2016.
 */
public class Bookmaker extends Person {
    public static final Bookmaker bookmaker = new Bookmaker("Алексей Анатольевич");

    private Bookmaker() {

    }

    public Bookmaker(String name) {
        this.setFirstName(name);
    }





}
