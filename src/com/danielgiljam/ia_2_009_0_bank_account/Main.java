package com.danielgiljam.ia_2_009_0_bank_account;

import com.danielgiljam.console_dialogue_api.ConsoleDialogueElement;
import com.danielgiljam.console_dialogue_api.ConsoleDialogueManager;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final List<BankAccount> bankAccounts = new Vector<>();
    private static final ScheduledExecutorService interestGenerator = Executors.newSingleThreadScheduledExecutor();

    private static int accountTypeDraft;
    private static int accountIdDraft;
    private static String clientDraft;
    private static int balanceDraft;

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
            "v2.0.0\n\n" +
            "© Daniel Giljam 2020\n" +
            "Den här kommandotolksappen är licensierad under MIT licensen.";

    private static final ConsoleDialogueElement CREATE_BANK_ACCOUNT = new ConsoleDialogueElement(
            () -> {
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl2 = new Vector<>();
                final String initialMessageLvl2 =
                        "1. Sparkonto\n" +
                        "2. Brukskonto";
                final String inputFeedLvl2 = ":";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl3 = new Vector<>();
                final String inputFeedLvl3 = "Namn:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl4 = new Vector<>();
                final String inputFeedLvl4 = "Kontonr:";
                final Vector<ConsoleDialogueElement> consoleDialogueElementsLvl5 = new Vector<>();
                final String inputFeedLvl5 = "Saldo:";
                final ConsoleDialogueManager createBankAccount = new ConsoleDialogueManager(consoleDialogueElementsLvl2, initialMessageLvl2, null, inputFeedLvl2, true, false);
                consoleDialogueElementsLvl2.add(new ConsoleDialogueElement(
                        () -> {
                            accountTypeDraft = Integer.parseInt(consoleDialogueElementsLvl2.elementAt(0).matcher.group(1));
                            new ConsoleDialogueManager(consoleDialogueElementsLvl3, null, null, inputFeedLvl3, false, false);
                        },
                        "(1|2)",
                        true
                ));
                consoleDialogueElementsLvl3.add(new ConsoleDialogueElement(
                        () -> {
                            clientDraft = consoleDialogueElementsLvl3.elementAt(0).matcher.group(1);
                            new ConsoleDialogueManager(consoleDialogueElementsLvl4, null, null, inputFeedLvl4, false, false);
                        },
                        "(.+)",
                        true
                ));
                consoleDialogueElementsLvl4.add(new ConsoleDialogueElement(
                        () -> {
                            accountIdDraft = Integer.parseInt(consoleDialogueElementsLvl4.elementAt(0).matcher.group(1));
                            new ConsoleDialogueManager(consoleDialogueElementsLvl5, null, null, inputFeedLvl5, false, false);
                        },
                        "(\\d+)",
                        true
                ));
                consoleDialogueElementsLvl5.add(new ConsoleDialogueElement(
                        () -> {
                            balanceDraft = Integer.parseInt(consoleDialogueElementsLvl5.elementAt(0).matcher.group(1));

                            bankAccounts.add(accountTypeDraft == 1
                                    ? new SavingsAccount(accountIdDraft, clientDraft, balanceDraft)
                                    : new CheckingAccount(accountIdDraft, clientDraft, balanceDraft)
                            );
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
                                printBalance(bankAccount);
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

    private static final ConsoleDialogueElement QUIT = new ConsoleDialogueElement(
            () -> {
                interestGenerator.shutdown();
                System.exit(0);
            }, "5", true);

    public static void main(String[] args) {
        interestGenerator.scheduleAtFixedRate(
                () -> {for (BankAccount bankAccount : bankAccounts) bankAccount.generateInterest();},
                0,
                10,
                TimeUnit.SECONDS
        );
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

    private static void printBalance(BankAccount bankAccount) {
        System.out.printf(
                "%nSaldo: %.2f%nRänteintäkter: %.2f%n",
                bankAccount.getBalance(),
                bankAccount.getTotalInterest()
        );
    }
}
