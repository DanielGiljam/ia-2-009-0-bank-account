package com.danielgiljam.ia_2_009_0_bank_account;

public class SavingsAccount extends BankAccount implements Depositable {
    public SavingsAccount(int accountId, String client, double balance) {
        super(accountId, client, balance, 0.1);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }
}
