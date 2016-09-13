package com.epam.ilya.model;

import org.joda.time.DateTime;

import java.io.InputStream;

public class Avatar extends BaseEntity {
    InputStream picture;
    DateTime creationDate;

    public Avatar() {
    }

    public InputStream getPicture() {
        return picture;
    }

    public void setPicture(InputStream picture) {
        this.picture = picture;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
