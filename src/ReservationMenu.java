//import org.joda.time.DateTime;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class ReservationMenu {
//
//    private Hotel hotel;
//
//    public ReservationMenu(Hotel hotel){this.hotel = hotel;}
//
//    /*
//     Hotel:
//        - add room  (OK)
//        - show rooms (OK)
//        - remove room (OK)
//
//     Room:
//        - room info (beds, prices...) (OK)
//
//     Reservation:
//        - show info (OK)
//        - change reservation (user, dates..., number of people) (OK)
//        - isItemReserved (OK)
//
//     User:
//        - show name (OK)
//
//
//      */
//
//    private Scanner sc = new Scanner(System.in);
//
//
//    public void showMenu() throws InterruptedException {
//
//        System.out.println(
//                "Hotel [1]\n" + "Room [2] \n" + "Reservation [3]\n" + "User [4]\n"
//        );
//
//        int choice = sc.nextInt();
//        switch (choice){
//            case 1:
//                hotelMenu();
//                break;
//            case 2:
//                roomMenu();
//                break;
//            case 3:
//                reservationMenu();
//                break;
//            case 4:
//                System.out.println("Exit [4]");
//                int choice1 = sc.nextInt();
//                if(choice1 == 1){
//                    System.exit(1);
//                }
//
//            default:
//                System.out.println("Invalid input");
//                Thread.sleep(120);
//                showMenu();
//
//
//        }
//
//    }
//    private void hotelMenu() throws InterruptedException {
//
//        System.out.println(
//                "Add room [1]\n" + "Show rooms [2]\n" + "Remove room [3]\n"
//
//        );
//        int choice = sc.nextInt();
//        switch (choice){
//            case 1:
//                System.out.println("Give parameters:");
//                // rooms, beds, price, floor
//                System.out.print("Rooms:");
//                int rooms = sc.nextInt();
//                System.out.print("Beds: ");
//                int beds = sc.nextInt();
//                System.out.print("Price: ");
//                double price = sc.nextDouble();
//                System.out.print("Floor: ");
//                int floor = sc.nextInt();
//                hotel.addRoom(new Room(rooms, beds, price, floor));
//                break;
//            case 2:
//                hotel.showRooms();
//                break;
//            case 3:
//                System.out.print("Give room id: ");
//                long roomId = sc.nextLong();
//                /*
//                for Rooms in RoomList{
//                    if room_id equals this.room_id
//                        remove this.room
//
//                 */
//                ArrayList<Room> hotelRooms = hotel.getRooms();
//                for(Room room : hotelRooms){
//                    if(room.getId() == roomId){
//                        hotel.removeRoom(room);
//                    }
//                }
//                break;
//            default:
//                System.out.println("Invalid input");
//                Thread.sleep(120);
//                showMenu();
//
//        }
//
//
//    }
//
//    private void roomMenu(){
//        System.out.println("Show info about room [1]");
//        int choice = sc.nextInt();
//
//        if(choice == 1){
//            System.out.println("Give room parameters: ");
//            System.out.print("Rooms:");
//            int rooms = sc.nextInt();
//            System.out.print("Beds: ");
//            int beds = sc.nextInt();
//            System.out.print("Price: ");
//            double price = sc.nextDouble();
//            System.out.print("Floor: ");
//            int floor = sc.nextInt();
//            new Room(rooms, beds, price, floor).roomInfo();
//        }
//
//    }
//
//    private void reservationMenu() {
//        System.out.println(
//                "Reservation info [1]\n" + "Change reservation [2]\n" + "Do reservation exists? [3]"
//        );
//        int choice = sc.nextInt();
//
//        switch (choice) {
//            case 1:
//                System.out.print("Give reservation id :");
//                long id = sc.nextLong();
//
//
//                File dir = new File("Reservations/");
//                File[] directoryListing = dir.listFiles(new FileFilter() {
//                    @Override
//                    public boolean accept(File pathname) {
//
//                        if (pathname.getName().endsWith(".rez")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//                ReservationService service = new ReservationService();
//                for (File file : directoryListing) {
//
//                    Reservation reservation = service.fromFile(file);
//                    if (reservation.getId() == id) {
//                        reservation.showInfo();
//                    }
//                }
//                break;
//            case 2:
//
//                ReservationService service1 = new ReservationService();
//                System.out.print("Give reservation ID: ");
//                long resid = sc.nextLong();
//
//                File dirr = new File("Reservations/");
//                File[] directoryListingg = dirr.listFiles(new FileFilter() {
//                    @Override
//                    public boolean accept(File pathname) {
//
//                        if (pathname.getName().endsWith(".rez")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//                for (File file : directoryListingg) {
//
//                    Reservation reservation = service1.fromFile(file);
//                    if (reservation.getId() == resid) {
//                        file.delete();
//                        System.out.println("Give new parameters: ");
//                        System.out.print("Item: ");
//
//                        System.out.print("Start date: ");
//                        DateTime startDate = service1.parseToDate(sc.next(), "dd/MM/yyyy");
//                        System.out.println("End date: ");
//                        DateTime endDate = service1.parseToDate(sc.next(), "dd/MM/yyyy");
//                        service1.toFile(new Reservation(reservation.getUser(), startDate, endDate));
//
//                    }
//                }
//                break;
//            case 3:
//                ReservationService service3 = new ReservationService();
//
//                File dirrr = new File("Reservations/");
//                File[] directoryListinggg = dirrr.listFiles(new FileFilter() {
//                    @Override
//                    public boolean accept(File pathname) {
//
//                        if (pathname.getName().endsWith(".rez")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//                System.out.println("Give reservation id");
//                long ressid = sc.nextLong();
//
//                for (File file : directoryListinggg) {
//                    Reservation reservation = service3.fromFile(file);
//
//                    if (reservation.getId() == ressid) {
//                        System.out.println("Yes!");
//                    }
//
//                }
//                System.out.println("No");
//                break;
//
//
//
//        }
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//}
