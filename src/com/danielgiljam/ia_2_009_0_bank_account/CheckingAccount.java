package com.danielgiljam.ia_2_009_0_bank_account;

import java.io.Serializable;

public class CheckingAccount extends BankAccount implements Depositable, Serializable {
    public CheckingAccount(String client, double balance) {
        super(client, balance, 0.01);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }
}
