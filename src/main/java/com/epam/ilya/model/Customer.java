package com.epam.ilya.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Bet> bets = new ArrayList<Bet>();


    public Customer() {

    }

    public Customer(String name) {
        this.setFirstName(name);
        setPersonsPurse(new CashAccount());
    }



    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }


    public void add(Bet bet) {
        getBets().add(bet);
    }
}
