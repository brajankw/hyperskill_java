package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    @JsonProperty("total_rows")
    private int ROWS;
    @JsonProperty("total_columns")
    private int COLS;
    @JsonProperty("available_seats")
    private List<Seat> availableSeats;

    @JsonIgnore
    private List<Purchase> purchasedSeats;

    public Room(int rows, int cols) {
        this.ROWS = rows;
        this.COLS = cols;
        this.availableSeats = new ArrayList<>();
        addSeats();
        this.purchasedSeats = new ArrayList<>();
    }

    @JsonProperty("total_rows")
    public int getROWS() {
        return ROWS;
    }
    @JsonProperty("total_columns")
    public int getCOLS() {
        return COLS;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setROWS(int ROWS) {
        this.ROWS = ROWS;
    }

    public void setCOLS(int COLS) {
        this.COLS = COLS;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Purchase> getPurchasedSeats() {
        return purchasedSeats;
    }

    private void addSeats() {
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLS; col++) {
                availableSeats.add(new Seat(row, col));
            }
        }
    }

    public Purchase purchaseSeat(Seat toPurchaseSeat) {
        availableSeats.remove(toPurchaseSeat);
        UUID token = UUID.randomUUID();
        Purchase purchase = new Purchase(toPurchaseSeat, token);
        purchasedSeats.add(purchase);
        return purchase;
    }

    public Seat returnSeat(UUID uuid) {
        for (int i = 0; i < purchasedSeats.size(); i++) {
            Purchase purchase = purchasedSeats.get(i);
            if (purchase.getToken().equals(uuid)) {
                Seat returnSeat = purchase.getSeat();
                purchasedSeats.remove(purchase);
                availableSeats.add(returnSeat);
                return returnSeat;
            }
        }
        return null;
    }

    public Object stats() {
        int income = 0;
        for (Purchase purchase: purchasedSeats) {
            income += purchase.getSeat().getPrice();
        }
        record RoomStats(
                @JsonProperty("current_income") int income,
                @JsonProperty("number_of_available_seats") int availableSeatsCount,
                @JsonProperty("number_of_purchased_tickets") int purchasedSeatsCount) {}
        return new RoomStats(income, availableSeats.size(), purchasedSeats.size());
    }
}

