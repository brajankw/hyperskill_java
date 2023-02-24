package banking;

public class Card {
    private String cardId;
    private String cardPin;
    private int balance;

    public Card(String cardId, String cardPin){
        this(cardId, cardPin, 0);
    }

    public Card(String cardId, String cardPin, int balance){
        this.cardId = cardId;
        this.cardPin = cardPin;
        this.balance = balance;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardPin() {
        return cardPin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
