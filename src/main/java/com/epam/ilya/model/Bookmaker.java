package com.epam.ilya.model;

public class Bookmaker extends Person {

    public Bookmaker() {

    }


    public Bookmaker(String firstName, String lastName, String password, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setEmail(email);
    }
}
