package com.epam.ilya.model;

import java.io.InputStream;

public class Customer extends Person {
    public InputStream avatar;

    public Customer() {

    }

    public Customer(String name) {
        this.setFirstName(name);
        setPersonsPurse(new CashAccount());
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(InputStream avatar) {
        this.avatar = avatar;
    }
}
