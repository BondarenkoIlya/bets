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

    @Override
    public String toString() {
        return "Avatar{" +
                "id="+ getId()+
                "creationDate=" + creationDate +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + getId();
        hash = hash*37 + picture.hashCode();
        hash = hash*37 + creationDate.hashCode();
        return hash;
    }
}
