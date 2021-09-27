package cinema.factories;

import cinema.domain.Seat;

import java.util.ArrayList;

public class SeatFactory {

    public static ArrayList<Seat> seats() {
        ArrayList<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                int price = i <= 4 ? 10 : 8;
                seats.add(new Seat(i, j, price));
            }
        }

        return seats;
    }
}
