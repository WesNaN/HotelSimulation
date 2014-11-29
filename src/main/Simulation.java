package main;

import service.DataService;
import service.files.FilesService;
import ui.gui.GraphicalInterface;


public class Simulation {

    public static DataService ds;

    public static void main(String[] args) {

        FilesService fs = new FilesService();
        Application app = new Application(fs);
        GraphicalInterface gui = new GraphicalInterface(app);









//        Hotel hotel = new Hotel("Hotel");
//
//        Room room1 = null;
//        User user = null;
//        try {
//            room1 = new Room(3, 4, 400);
//            user = new User("Kuba", 43512);
//            Reservation res1 = new Reservation(user, new Date(2014, 12, 10), new Date(2014, 12, 14), room1.getRoomID());
//        } catch (ConnectionError connectionError) {
//            connectionError.printStackTrace();
//        }
//        hotel.addRoom(room1);


    }




}
