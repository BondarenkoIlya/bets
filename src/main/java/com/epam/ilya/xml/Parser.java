package com.epam.ilya.xml;

import com.epam.ilya.exceptions.ParseException;

import java.util.List;

public interface Parser {
    <T> List<T> parse (String xmlFileName , Class<T> clazz ) throws ParseException;
}
