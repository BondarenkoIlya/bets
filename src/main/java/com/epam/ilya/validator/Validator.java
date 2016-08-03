package com.epam.ilya.validator;

public class Validator {
    public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD = "^.{6,}$";
    public static final String NOT_EMPTY_TEXT = "^\\p{L}+{1,}$";
    public static final String NOT_EMPTY_NUMBER = "^\\p{N}+{1,}$";
    public static final String MONEY = "^[1-9]\\d{0,8}$";

    public boolean validate(final String input, String regex) {
        return input.matches(regex);
    }
}
