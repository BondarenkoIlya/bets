package com.epam.ilya.model;

/**
 * Bookmaker - person who create new match (event) for which bets may be accepted,
 * sum up result of this match and mare money transfer to customers
 *
 * @author Bondarenko Ilya
 */

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
