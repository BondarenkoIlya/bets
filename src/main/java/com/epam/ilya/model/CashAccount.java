package com.epam.ilya.model;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
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

    public void removeCash(Money amount) throws CashAccountBalanceExceptions {
        if (balanceAvailabilityFor(amount)) {
            setBalance(getBalance().minus(amount));
        } else {
            throw new CashAccountBalanceExceptions();
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
        return
                "balance=" + balance;
    }
}
