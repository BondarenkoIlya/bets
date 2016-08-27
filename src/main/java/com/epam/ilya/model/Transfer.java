package com.epam.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Transfer extends BaseEntity {
    private Person sender;
    private Person recipient;
    private Money amount;
    private DateTime time;

    public Transfer() {
    }

    public Transfer(Person sender, Person recipient, Money amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.time = DateTime.now();
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}
