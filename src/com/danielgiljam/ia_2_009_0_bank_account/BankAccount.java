package com.danielgiljam.ia_2_009_0_bank_account;

import java.io.Serializable;

public abstract class BankAccount implements Serializable {
    protected final String client;
    protected double balance;
    protected double interestRate;

    protected double totalInterest = 0;

    public BankAccount(String client, double balance, double interestRate) {
        this.client = client;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public String getClient() {
        return client;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void generateInterest() {
        final double interestChunk = interestRate * balance;
        totalInterest += interestChunk;
        balance += interestChunk;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void withdraw(double amount) {
        totalInterest *= !(amount <= 0) ? amount / this.balance : 0;
        balance -= amount;
    }
}
