package com.epam.ilya.model;

/**
 * Bookmaker class describes person who create new match (event) for which bets may be accepted,
 * sum up result of this match and make money transfers to customers.
 *
 * @author Bondarenko Ilya
 */

public class Bookmaker extends Person {

    public static final String EMAIL = "qwe@mail.ru";

    public Bookmaker() {
    }

    public Bookmaker(String firstName, String lastName, String password, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setEmail(email);
    }

}
