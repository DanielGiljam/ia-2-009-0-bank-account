package com.danielgiljam.ia_2_009_0_bank_account;

import java.io.Serializable;

public class CreditAccount extends BankAccount implements Serializable {
    public CreditAccount(String client) {
        super(client, 0, 0.2);
    }

    @Override
    public void withdraw(double amount) {
        final double newBalance = balance - amount;
        if (newBalance <= -1000) {
            System.out.println("Kunde inte ta ut pengar eftersom din kredit är maxad.");
        } else {
            balance -= amount;
        }
    }
}
