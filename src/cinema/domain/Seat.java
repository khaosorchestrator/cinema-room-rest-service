package cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Seat {

    @JsonIgnore
    private final String token = new Token().toString();

    @JsonIgnore
    private boolean taken = false;

    @NonNull
    private int row;
    @NonNull
    private int column;
    @NonNull
    private int price;

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