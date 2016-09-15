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

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + getId();
        hash = hash*37 + getEmail().hashCode();
        hash = hash*37 + getFirstName().hashCode();
        hash = hash*37 + getLastName().hashCode();
        hash = hash*37 + getPersonsPurse().hashCode();
        hash = hash*37 + getPassword().hashCode();
        return hash;
    }
}
