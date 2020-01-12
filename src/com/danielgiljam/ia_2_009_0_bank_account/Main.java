package com.danielgiljam.ia_2_009_0_bank_account;

import com.danielgiljam.console_dialogue_api.ConsoleDialogueManager;
import com.danielgiljam.console_dialogue_api.ConsoleDialogueElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {

    private static final List<BankAccount> bankAccounts = new ArrayList<>();

    private static int accountIdDraft;
    private static String clientDraft;
    private static int balanceDraft;
    private static double rateDraft;

    private static BankAccount specificBankAccountReference;

    private static final String INITIAL_MESSAGE =
            "------------------------------------------------\n" +
            "1. Skapa nytt konto\n" +
            "2. Ta ut pengar från ett givet konto\n" +
            "3. Sätta in pengar på ett givet konto\n" +
            "4. Visa balansen för ett givet konto\n" +
            "5. Avsluta";

    private static final String HELP_MESSAGE =
            "Ingen hjälp.\n\n" +
            "------------------------------------------------\n" +
            "v1.0.0\n\n" +
            "© Daniel Giljam 2020\n" +
            "Den här kommandotolksappen är licensierad under MIT licensen.";

    private static final ConsoleDialogueElement CREATE_BANK_ACCOUNT = new ConsoleDialogueElement(
            () -> {
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl2 = new Vector<>();
                final String inputFeedLvl2 = "Namn:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl3 = new Vector<>();
                final String inputFeedLvl3 = "Kontonr:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl4 = new Vector<>();
                final String inputFeedLvl4 = "Balans:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl5 = new Vector<>();
                final String inputFeedLvl5 = "Ränta:";
                final ConsoleDialogueManager createBankAccount = new ConsoleDialogueManager(consoleDialogueElementsLvl2, null, null, inputFeedLvl2, true, false);
                consoleDialogueElementsLvl2.add(new ConsoleDialogueElement(
                        () -> {
                            clientDraft = consoleDialogueElementsLvl2.elementAt(0).matcher.group(1);
                            new ConsoleDialogueManager(consoleDialogueElementsLvl3, null, null, inputFeedLvl3, false, false);
                        },
                        "(.+)",
                        true
                ));
                consoleDialogueElementsLvl3.add(new ConsoleDialogueElement(
                        () -> {
                            accountIdDraft = Integer.parseInt(consoleDialogueElementsLvl3.elementAt(0).matcher.group(1));
                            new ConsoleDialogueManager(consoleDialogueElementsLvl4, null, null, inputFeedLvl4, false, false);
                        },
                        "(\\d+)",
                        true
                ));
                consoleDialogueElementsLvl4.add(new ConsoleDialogueElement(
                        () -> {
                            balanceDraft = Integer.parseInt(consoleDialogueElementsLvl4.elementAt(0).matcher.group(1));
                            new ConsoleDialogueManager(consoleDialogueElementsLvl5, null, null, inputFeedLvl5, false, false);
                        },
                        "(\\d+)",
                        true
                ));
                consoleDialogueElementsLvl5.add(new ConsoleDialogueElement(
                        () -> {
                            rateDraft = Double.parseDouble(consoleDialogueElementsLvl5.elementAt(0).matcher.group(1).replace(",", "."));
                            bankAccounts.add(new BankAccount(accountIdDraft, clientDraft, balanceDraft, rateDraft));
                            printInitialMessage();
                        },
                        "(\\d+(?:[.,]\\d+)?)",
                        true
                ));
                createBankAccount.ConsoleDialogue();
            },
            "1",
            false
    );

    private static final ConsoleDialogueElement INTERACT = new ConsoleDialogueElement(
            () -> {
                final int chosenAction = Integer.parseInt(Main.INTERACT.matcher.group(1));
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl2 = new Vector<>();
                final String inputFeedLvl2 = "Kontonr:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl3 = new Vector<>();
                final String inputFeedLvl3 = "Summa:";
                final ConsoleDialogueManager withdraw = new ConsoleDialogueManager(consoleDialogueElementsLvl2, null, null, inputFeedLvl2, true, false);
                consoleDialogueElementsLvl2.add(new ConsoleDialogueElement(
                        () -> {
                            final int accountId = Integer.parseInt(consoleDialogueElementsLvl2.elementAt(0).matcher.group(1));
                            final BankAccount bankAccount = getBankAccount(accountId);
                            if (bankAccount == null) {
                                System.out.println("Det finns inget konto med det numret.");
                                printInitialMessage();
                            } else if (chosenAction == 4) {
                                System.out.println(bankAccount.getBalance());
                                printInitialMessage();
                            } else {
                                specificBankAccountReference = bankAccount;
                                new ConsoleDialogueManager(consoleDialogueElementsLvl3, null, null, inputFeedLvl3, false, false);
                            }
                        },
                        "(\\d+)",
                        true
                ));
                consoleDialogueElementsLvl3.add(new ConsoleDialogueElement(
                        () -> {
                            final int amount = Integer.parseInt(consoleDialogueElementsLvl3.elementAt(0).matcher.group(1));
                            if (chosenAction == 2) specificBankAccountReference.withdraw(amount);
                            else if (chosenAction == 3) specificBankAccountReference.deposit(amount);
                            else System.out.println("Något omöjligt hände--\n*djupt andetag*\n*visar vi huvudmenyn igen*");;
                            printInitialMessage();
                        },
                        "(\\d+)",
                        true
                ));
                withdraw.ConsoleDialogue();
            },
            "(2|3|4)",
            false
    );

    private static final ConsoleDialogueElement QUIT = new ConsoleDialogueElement(() -> System.exit(0), "5", true);

    public static void main(String[] args) {
        Vector<ConsoleDialogueElement> consoleDialogueElements = new Vector<>();
        consoleDialogueElements.add(CREATE_BANK_ACCOUNT);
        consoleDialogueElements.add(INTERACT);
        consoleDialogueElements.add(QUIT);
        new ConsoleDialogueManager(consoleDialogueElements, INITIAL_MESSAGE, HELP_MESSAGE, ":", false, false);
    }

    private static void printInitialMessage() {
        System.out.println("\n" + INITIAL_MESSAGE);
    }

    private static BankAccount getBankAccount(int accountId) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountId() == accountId) return bankAccount;
        }
        return null;
    }
}
