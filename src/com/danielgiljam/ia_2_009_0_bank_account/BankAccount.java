package com.danielgiljam.ia_2_009_0_bank_account;

public class BankAccount {
    private final int accountId;
    private final String client;
    private int balance;
    private final double rate;

    public BankAccount(int accountId, String client, int balance, double rate) {
        this.accountId = accountId;
        this.client = client;
        this.balance = balance;
        this.rate = rate;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getClient() {
        return client;
    }

    public int getBalance() {
        return balance;
    }

    public double getRate() {
        return rate;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }
}
