package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    private final Database db;
    private final Random random;

    public Bank(Database db) {
        random = new Random();
        this.db = db;
    }

    public Card addCard(){
        String cardId = createCardId();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 4; i++){
            int number = random.nextInt(10);
            builder.append(number);
        }
        String cardPin = builder.toString();
        Card card = new Card(cardId, cardPin);
        db.insert(cardId, cardPin);
        return card;
    }

    private String createCardId(){
        StringBuilder builder = new StringBuilder("400000");
        for(int i = 0; i < 10; i++){
            int number = random.nextInt(10);
            builder.append(number);
        }
        String cardId = builder.toString();
        if (!luhnCardIdCheck(cardId)) return createCardId();
        if (checkIfExist(cardId)) return createCardId();
        return  cardId;
    }

    public boolean luhnCardIdCheck(String cardId){
        List<Integer> cardIdNumbers = new ArrayList<>();
        String[] numbers = cardId.split("");

        for (String number: numbers) cardIdNumbers.add(Integer.valueOf(number));
        int checkSum = 0;
        for (int i = 0; i < cardIdNumbers.size() - 1; i++) {
            if(i % 2 == 0) {
                int value = cardIdNumbers.get(i) * 2;
                if(value > 9) value -= 9;
                cardIdNumbers.set(i, value);
            }
            checkSum += cardIdNumbers.get(i);
        }

        return (checkSum + cardIdNumbers.get(cardIdNumbers.size() - 1)) % 10 == 0;
    }

    public Card checkCredentials(String cardId, String cardPin) {
        return this.db.checkCredentials(cardId, cardPin);
    }
    public boolean checkIfExist(String cardId) {return this.db.checkIfExist(cardId);}
    public void addBalance(Card card, int amount) {
        card.setBalance(card.getBalance() + amount);
        this.db.update(card.getCardId(), card.getBalance());
    }

    public void transferBalance(Card payerCard, String receiverCardId, int transferAmount) {
        payerCard.setBalance(payerCard.getBalance() - transferAmount);
        this.db.update(payerCard.getCardId(), payerCard.getBalance());
        this.db.update(receiverCardId, transferAmount);

    }

    public void deleteCard(String cardId) {
        this.db.delete(cardId);
    }
}
