package cinema.controllers;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.domain.Token;
import cinema.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class CinemaController {

    private final Cinema cinema = new Cinema();

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> purchase(@RequestBody Seat seat) {
        if (seat.getRow() > 9 || seat.getColumn() > 9 || seat.getRow() < 1 || seat.getColumn() < 1)
            return new ResponseEntity<>(ErrorMessage.OUT_OF_BOUNDS, HttpStatus.BAD_REQUEST);

        ArrayList<Seat> seats = cinema.getSeats();
        int position = cinema.getArrPosition(seat.getRow(), seat.getColumn());

        Seat chosenSeat = seats.get(position);

        if (chosenSeat.isTaken())
            return new ResponseEntity<>(ErrorMessage.ALREADY_PURCHASED, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(chosenSeat.getPurchased(), HttpStatus.OK);
    }

    @PostMapping(path = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> returnTicket(@RequestBody Token token) {
        Optional<Seat> seat = cinema.findSeat(token);

        if (seat.isEmpty())
            return new ResponseEntity<>(ErrorMessage.WRONG_TOKEN, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(seat.get().returnSeat(), HttpStatus.OK);
    }

    @PostMapping(path = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStats(@RequestParam(defaultValue = "") @RequestBody String password) {
        if (password.equals("super_secret"))
            return new ResponseEntity<>(cinema.getStats(), HttpStatus.OK);
        return new ResponseEntity<>(ErrorMessage.WRONG_PASSWORD, HttpStatus.UNAUTHORIZED);
    }

}