package com.epam.ilya.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 09.02.2016.
 */
public class Customer extends Person {
    private List<Bet> bets = new ArrayList<Bet>();

    public Customer() {

    }

    public Customer(String name){
        this.setName(name);
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
