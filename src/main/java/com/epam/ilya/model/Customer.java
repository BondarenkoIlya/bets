package com.epam.ilya.model;

public class Customer extends Person {
    private Avatar avatar;

    public Customer() {

    }

    public Customer(String name) {
        this.setFirstName(name);
        setPersonsPurse(new CashAccount());
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                "avatar=" + (avatar != null) +
                '}';
    }

}
