package com.epam.ilya.model;

public class Bookmaker extends Person {
    public static final Bookmaker bookmaker = new Bookmaker("Алексей", "Пак" , "1234567","qwe@mail.ru");

    private Bookmaker() {

    }


    public Bookmaker(String firstName, String lastName, String password, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setEmail(email);
    }
}
