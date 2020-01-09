package com.danielgiljam.ia_2_009_0_bank_account;

//********************************************************************
//  Transactions.java       Author: Lewis/Loftus
//
//  Demonstrates the creation and use of multiple Account objects.
//********************************************************************

import com.danielgiljam.ia_2_009_0_bank_account.resources.ResourceDistributor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Transactions
{
   //-----------------------------------------------------------------
   //  Creates some bank accounts and requests various services.
   //-----------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        Account acct1 = new Account ("Ted Murphy", 72354, 102.56);
        Account acct2 = new Account ("Jane Smith", 69713, 40.00);
        Account acct3 = new Account ("Edward Demsey", 93757, 759.32);

        acct1.deposit (25.85);

        double smithBalance = acct2.deposit (500.00);
        System.out.println ("Smith balance after deposit: " +
                smithBalance);

        System.out.println ("Smith balance after withdrawal: " +
                acct2.withdraw (430.75));

        // Testing XML parsing...
        ResourceDistributor rd = ResourceDistributor.initialize();
    }
}
