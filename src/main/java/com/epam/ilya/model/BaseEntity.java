package com.epam.ilya.model;

import java.util.UUID;

/**
 * Created by Дом on 06.03.2016.
 */
public abstract class BaseEntity {
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

}
