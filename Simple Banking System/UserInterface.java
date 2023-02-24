package banking;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final Bank bank;

    public UserInterface(Bank bank) {
        scanner = new Scanner(System.in);
        this.bank = bank;
    }

    public void mainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        String userChoice = scanner.nextLine();

        switch (userChoice) {
            case "1" -> {
                Card card = bank.addCard();

                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(card.getCardId());
                System.out.println("Your card PIN:");
                System.out.println(card.getCardPin());
                mainMenu();

            }
            case "2" -> {
                System.out.println("Enter your card number:");
                String userCardId = scanner.nextLine();
                System.out.println("Enter your PIN:");
                String userCardPin = scanner.nextLine();
                Card card = bank.checkCredentials(userCardId, userCardPin);
                if (card != null) {
                    System.out.println("You have successfully logged in!");
                    userMenu(card);

                } else {
                    System.out.println("Wrong card number or PIN!");
                    mainMenu();
                }

            }
            case "0" -> System.out.println("Bye!");
        }

    }

    public void userMenu(Card card) {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");

        String userChoice = scanner.nextLine();
        System.out.println("im in");
        switch (userChoice) {
            case "1" -> {
                System.out.println("Balance: " + card.getBalance());
                userMenu(card);
            }
            case "2" -> {
                System.out.println("Enter income:");
                int incomeValue = Integer.parseInt(scanner.nextLine());
                bank.addBalance(card, incomeValue);
                System.out.println("Income was added!");

                userMenu(card);
            }
            case "3" -> {
                System.out.println("Transfer");
                System.out.println("Enter card number");
                String receiverCardId = scanner.nextLine();
                if (receiverCardId.equals(card.getCardId())) {
                    System.out.println("You can't transfer money to the same account!");
                } else if (!bank.luhnCardIdCheck(receiverCardId)) {
                    System.out.println("Probably you made a mistake in the card number. Please try again!");
                } else if (!bank.checkIfExist(receiverCardId)) {
                    System.out.println("Such a card does not exist.");
                } else {
                    System.out.println("Enter how much money you want to transfer:");
                    int transferAmount = Integer.parseInt(scanner.nextLine());
                    if (transferAmount > card.getBalance()) {
                        System.out.println("Not enough money!");
                    } else {
                        bank.transferBalance(card, receiverCardId, transferAmount);
                        System.out.println("Success!");
                    }
                }
                userMenu(card);
            }
            case "4" -> {
                bank.deleteCard(card.getCardId());
                System.out.println("The account has been closed!");
                mainMenu();
            }
            case "5" -> mainMenu();
            case "0" -> System.out.println("Bye!");
        }
    }
}
