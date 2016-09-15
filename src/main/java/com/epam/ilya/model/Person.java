package com.epam.ilya.model;

public abstract class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private CashAccount personsPurse;

    @Override
    public String toString() {
        return "Person{" +
                "id='" + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CashAccount getPersonsPurse() {
        return personsPurse;
    }

    public void setPersonsPurse(CashAccount customersPurse) {
        this.personsPurse = customersPurse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return hash;
    }
}




