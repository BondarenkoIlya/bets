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
                "avatar=" + (avatar!=null) +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + getId();
        hash = hash*37 + getEmail().hashCode();
        hash = hash*37 + getFirstName().hashCode();
        hash = hash*37 + getLastName().hashCode();
        hash = hash*37 + getPersonsPurse().hashCode();
        hash = hash*37 + getPassword().hashCode();
        hash = hash*37 + getAvatar().hashCode();
        return hash;
    }
}
