package com.epam.ilya.model;

import java.util.UUID;

/**
 * Created by Дом on 10.02.2016.
 */
abstract class Person {
    private UUID id = UUID.randomUUID();
    private String name;

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", name='" + name + '\'';
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}




