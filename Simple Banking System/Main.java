package banking;

public class Main {
    public static void main(String[] args) {
        Database db = new Database(args[1]);
        Bank bank = new Bank(db);
        UserInterface UI = new UserInterface(bank);
        UI.mainMenu();
    }
}