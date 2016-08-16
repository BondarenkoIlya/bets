package com.epam.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class Transfer extends BaseEntity {
    private Person senderId;
    private Person recipientId;
    private Money amount;
    private DateTime time;

    public Transfer() {
    }

    public Transfer(Person senderId, Person recipientId, Money amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.time = DateTime.now();
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public Person getSenderId() {
        return senderId;
    }

    public void setSenderId(Person senderId) {
        this.senderId = senderId;
    }

    public Person getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Person recipientId) {
        this.recipientId = recipientId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
