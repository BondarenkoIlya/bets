package com.epam.ilya.model;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class CashAccount extends BaseEntity {
    private Money balance;
    private Person person;

    public CashAccount() {
        setBalance(Money.of(CurrencyUnit.of("KZT"), 0));

    }

    public CashAccount(Money balance, Person person) {
        this.setBalance(balance);
        this.setPerson(person);
    }

    public void addCash(Money amount) {
        this.setBalance(getBalance().plus(amount));
    }

    public boolean removeCash(Money amount) {
        if (balanceAvailabilityFor(amount)) {
            setBalance(getBalance().minus(amount));
            return true;
        } else {
            return false;
        }
    }

    public boolean balanceAvailabilityFor(Money checkAmount) {
        if (getBalance().isLessThan(checkAmount)) {
            return false;
        } else {
            return true;
        }
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "CashAccount{" +
                "id=" + getId() +
                ", balance=" + balance +
                ", person=" + person +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*37 + getId();
        hash = hash*37 + balance.hashCode();
        hash = hash*37 + person.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if (!(obj instanceof CashAccount)){
            return false;
        }else {
            CashAccount purse = (CashAccount) obj;
            return this.hashCode()==purse.hashCode();
        }
    }
}
