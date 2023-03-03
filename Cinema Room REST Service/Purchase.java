package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Purchase {
    @JsonProperty("token")
    private final UUID token;
    @JsonProperty("ticket")
    private final Seat seat;

    public Purchase(Seat seat, UUID token) {
        this.token = token;
        this.seat = seat;
    }

    public UUID getToken() {
        return token;
    }

    public Seat getSeat() {
        return seat;
    }

}
