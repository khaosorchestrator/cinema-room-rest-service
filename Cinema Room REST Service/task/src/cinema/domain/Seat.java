package cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Seat {

    @JsonIgnore
    private final String token = new Token().toString();

    @JsonIgnore
    private boolean taken = false;
    private int row;
    private int column;
    private int price;

    public Seat() {}
    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getPrice() {
        return price;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getToken() {
        return token;
    }

    public boolean isTaken() {
        return taken;
    }

    @JsonIgnore
    public ObjectNode getPurchased() {
        setTaken(true);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode place = mapper.createObjectNode();

        place.put("row", getRow());
        place.put("column", getColumn());
        place.put("price", getPrice());

        ObjectNode returnedTicket = mapper.createObjectNode();

        returnedTicket.put("token", getToken());
        returnedTicket.set("ticket", place);

        return returnedTicket;
    }

    @JsonIgnore
    public ObjectNode returnSeat() {
        setTaken(false);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode place = mapper.createObjectNode();

        place.put("row", getRow());
        place.put("column", getColumn());
        place.put("price", getPrice());

        ObjectNode returnedTicket = mapper.createObjectNode();
        returnedTicket.set("returned_ticket", place);

        return returnedTicket;
    }
}