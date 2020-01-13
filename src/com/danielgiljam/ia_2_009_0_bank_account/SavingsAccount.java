package com.danielgiljam.ia_2_009_0_bank_account;

import java.io.Serializable;

public class SavingsAccount extends BankAccount implements Depositable, Serializable {
    public SavingsAccount(String client, double balance) {
        super(client, balance, 0.1);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }
}
