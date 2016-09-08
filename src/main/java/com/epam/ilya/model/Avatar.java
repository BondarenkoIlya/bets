package com.epam.ilya.model;

import java.io.InputStream;

public class Avatar extends BaseEntity {
    InputStream picture;

    public Avatar() {
    }

    public InputStream getPicture() {
        return picture;
    }

    public void setPicture(InputStream picture) {
        this.picture = picture;
    }
}
