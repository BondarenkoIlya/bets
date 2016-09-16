package com.epam.ilya.model;

/**
 * Abstract class for add id to all entities
 *
 * @author Bondarenko Ilya
 */

abstract class BaseEntity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
