package com.danielgiljam.ia_2_009_0_bank_account.example;

//********************************************************************
//  Account.java       Author: Lewis/Loftus
//
//  Represents a bank account with basic services such as deposit
//  and withdraw.
//********************************************************************

import java.text.NumberFormat;

public class Account
{
    private final double RATE = 0.035;  // interest rate of 3.5%

    private long acctNumber;
    private double balance;
    private String name;

    //-----------------------------------------------------------------
    //  Sets up the account by defining its owner, account number,
    //  and initial balance.
    //-----------------------------------------------------------------
    public Account (String owner, long account, double initial)
    {
        name = owner;
        acctNumber = account;
        balance = initial;
    }

    //-----------------------------------------------------------------
    //  Deposits the specified amount into the account. Returns the
    //  new balance.
    //-----------------------------------------------------------------
    public double deposit (double amount)
    {
        balance = balance + amount;

        return balance;
    }

    //-----------------------------------------------------------------
    //  Withdraws the specified amount from the account and applies
    //  the fee. Returns the new balance.
    //-----------------------------------------------------------------
    public double withdraw (double amount)
    {
        balance = balance - amount;

        return balance;
    }

    //-----------------------------------------------------------------
    //  Adds interest to the account and returns the new balance.
    //-----------------------------------------------------------------
    public double addInterest ()
    {
        balance += (balance * RATE);
        return balance;
    }

    //-----------------------------------------------------------------
    //  Returns the current balance of the account.
    //-----------------------------------------------------------------
    public double getBalance ()
    {
        return balance;
    }
}