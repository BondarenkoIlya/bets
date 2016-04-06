package com.epam.ilya.model;

import java.util.UUID;

public abstract class Person extends BaseEntity {
    private String name;
    private CashAccount personsPurse;

    @Override
    public String toString() {
        return
                "Name='" + name + '\'' +
                ", customersPurse's" + personsPurse +
                '}';
    }

    public CashAccount getPersonsPurse() {
        return personsPurse;
    }

    public void setPersonsPurse(CashAccount customersPurse) {
        this.personsPurse = customersPurse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}




