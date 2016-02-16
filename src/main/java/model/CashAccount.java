package model;

import org.joda.money.Money;

/**
 * Created by Дом on 14.02.2016.
 */
public class CashAccount {
    private String holdersName;
    private Money balance;


    public String getHoldersName() {
        return holdersName;
    }

    public void setHoldersName(String holdersName) {
        this.holdersName = holdersName;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }


}
