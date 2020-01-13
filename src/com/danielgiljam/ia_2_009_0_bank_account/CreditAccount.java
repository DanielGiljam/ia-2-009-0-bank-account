package com.danielgiljam.ia_2_009_0_bank_account;

public class CreditAccount extends BankAccount {
    public CreditAccount(int accountId, String client) {
        super(accountId, client, 0, 0.2);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    public boolean belowThreshold() {
        return balance >= -1000;
    }
}
