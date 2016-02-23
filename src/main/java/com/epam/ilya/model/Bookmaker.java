package com.epam.ilya.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Bookmaker extends Person {

    private Bookmaker() {

    }

    private Bookmaker(String name) {
        this.setName(name);
    }

    public static final Bookmaker bookmaker = new Bookmaker("Вася");



}
