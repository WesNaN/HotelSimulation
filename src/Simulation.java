import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;


public class Simulation {

    static DataService ds;

    public static void main(String[] args) throws ReservationException, IOException, SQLException, ClassNotFoundException {

        Oracle service = new Oracle();
        service.connect();
        service.cleanDatabase();

        ds = service;


        Hotel hotel = new Hotel("Hotel");

        Room room1 = new Room(3, 4, 400);
        hotel.addRoom(room1);

        User user = new User("Kuba", 43512);

        Reservation res1 = new Reservation(user, new Date(2014, 12, 10), new Date(2014, 12, 14), room1.getRoomID());











    }




}
