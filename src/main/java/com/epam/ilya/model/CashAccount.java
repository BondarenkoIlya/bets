package com.epam.ilya.model;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Created by Дом on 05.03.2016.
 */
public class CashAccount {//стоит ли добавлять мапу транзакций по времени и значению
    private Money balance;
    private Person person;
    //private Map<DateTime,Money> transactions = new Map<DateTime, Money>();


    public CashAccount() {
    }

    public CashAccount(Money balance,Person person) {
        this.setBalance(balance);
        this.setPerson(person);
    }
    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public  void addCash(Money winsAmount){
        getBalance().plus(winsAmount);
    }
    public void removeCash (Money loseAmount) throws CashAccountBalanceExceptions {
        if (balanceAvailabilityFor(loseAmount)){
            getBalance().minus(loseAmount);
        }else{
            throw new CashAccountBalanceExceptions();
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean balanceAvailabilityFor(Money checkAmount){
        if(getBalance().isLessThan(checkAmount)){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String toString() {
        return
                "balance=" + balance;
    }
}
