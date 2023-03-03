package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class RoomController {
    final Room room = new Room(9, 9);

    @GetMapping("/seats")
    public Room getSeats() {
        return room;
    }

    @PostMapping("/purchase")
    public Purchase purchaseSeat(@RequestBody Seat seatToPurchase) {
        if (seatToPurchase.getRow() > room.getROWS() || seatToPurchase.getRow() < 1 || seatToPurchase.getColumn() > room.getCOLS() || seatToPurchase.getColumn() < 1) {
            throw new CustomInvalidException("The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
        }
        if (room.getAvailableSeats().contains(seatToPurchase)) {
            return room.purchaseSeat(seatToPurchase);
        } else {
            throw new CustomInvalidException("The ticket has been already purchased!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public Object returnSeat(@RequestBody Map<String, String> request) {
        UUID uuid = UUID.fromString(request.get("token"));
        Seat returnSeat = room.returnSeat(uuid);
        if (returnSeat == null) throw new CustomInvalidException("Wrong token!", HttpStatus.BAD_REQUEST);
        record TicketToReturn(@JsonProperty("returned_ticket") Seat seat) {}
        return new TicketToReturn(returnSeat);
    }

    @PostMapping("/stats")
    public Object roomStats(@RequestParam(required = false) String password) {
        if(password == null) throw new CustomInvalidException("The password is wrong!", HttpStatus.UNAUTHORIZED);
        if (!password.equals("super_secret")) throw new CustomInvalidException("The password is wrong!", HttpStatus.UNAUTHORIZED);
        return room.stats();
    }

    @ExceptionHandler(CustomInvalidException.class)
    public ResponseEntity<CustomErrorMessage> handleCustomInvalid(
            CustomInvalidException e) {

        CustomErrorMessage body = new CustomErrorMessage(
                e.getMessage());

        return new ResponseEntity<>(body, e.getError());
    }

}
