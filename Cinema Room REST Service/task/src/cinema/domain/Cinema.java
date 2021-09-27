package cinema.domain;

import cinema.factories.SeatFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cinema {

    @JsonProperty("total_rows")
    private final int totalRows;

    @JsonProperty("total_columns")
    private final int totalColumns;

    @JsonProperty("available_seats")
    private final ArrayList<Seat> seats;

    public Cinema() {
        totalRows = 9;
        totalColumns = 9;
        seats = SeatFactory.seats();
    }

    @JsonIgnore
    public Optional<Seat> findSeat(Token token) {
        return seats.stream().filter(seat -> seat.getToken().equals(token.toString())).findFirst();
    }

    @JsonIgnore
    public int getArrPosition(int row, int col) {
        return (row - 1) * 9 + col - 1;
    }

    @JsonIgnore
    public List<Seat> getTakenSeats() {
        return seats.stream().filter(Seat::isTaken).collect(Collectors.toList());
    }

    @JsonIgnore
    public int getAvailableSeats() {
        return seats.size() - getTakenSeats().size();
    }

    @JsonIgnore
    public int getPurchasedSeats() {
        return getTakenSeats().size();
    }

    @JsonIgnore
    public int getCurrentIncome() {
        return getTakenSeats().stream().
                mapToInt(Seat::getPrice).sum();
    }

    @JsonIgnore
    public String getStats() {
        return String.format("{\n" +
                        "    \"current_income\": %s,\n" +
                        "    \"number_of_available_seats\": %s,\n" +
                        "    \"number_of_purchased_tickets\": %s\n" +
                        "}",
                getCurrentIncome(),
                getAvailableSeats(),
                getPurchasedSeats());
    }

    @JsonIgnore
    public ArrayList<Seat> getSeats() {
        return seats;
    }
}