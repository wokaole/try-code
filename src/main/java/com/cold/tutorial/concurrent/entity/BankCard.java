package com.cold.tutorial.concurrent.entity;

/**
 * Created by hui.liao on 2015/9/7.
 */
public class BankCard {
    private String cardId = "JUC123456";
    private int balance = 1000000;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
