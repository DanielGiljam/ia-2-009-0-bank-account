package com.danielgiljam.ia_2_009_0_bank_account;

public class CheckingAccount extends BankAccount {
    public CheckingAccount(int accountId, String client, double balance) {
        super(accountId, client, balance, 0.01);
    }
}
