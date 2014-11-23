//import org.joda.time.DateTime;
//import org.joda.time.Interval;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//
//import java.io.*;
//
//public class ReservationService {
//
//    public ReservationService(){
//
//    }
//
//
//
//    public Reservation create(User user, String start, String end)
//            throws IOException, ReservationException {
//
//        DateTime startDate = parseToDate(start, user.getDateFormat());
//        DateTime endDate  = parseToDate(end, user.getDateFormat());
//
//        if(!isOccupied(startDate, endDate)){
//
//            Reservation reservation  = new Reservation(user, startDate, endDate);
//            toFile(reservation);
//            return reservation;
//        }
//        System.out.println("Reservation already exits");
//        return null;
//    }
//
//
//    public void delete(Reservation reservation){
//
//        File dir = new File("Reservations/");
//        File[] directoryListing = dir.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//
//                if(pathname.getName().endsWith(".rez")){
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        for(File file : directoryListing){
//
//            if(fromFile(file).equals(reservation)){
//                file.delete();
//            }
//        }
//
//
//    }
//
//
//    public void toFile(Reservation reservation) {
//        try {
//
//            FileOutputStream fileOut =
//                    new FileOutputStream("Reservations/" +  reservation.toString() + ".rez");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//
//            out.writeObject(reservation);
//            out.close();
//            fileOut.close();
//
//        } catch (Exception i) {
//            i.printStackTrace();
//        }
//    }
//
//    public Reservation fromFile(File file) {
//        try {
//            FileInputStream fileIn = new FileInputStream(file);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//
//            Reservation reservation = (Reservation) in.readObject();
//            in.close();
//            fileIn.close();
//            return reservation;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//    public DateTime parseToDate(String givenDate, String format){
//        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
//        return formatter.parseDateTime(givenDate);
//
//    }
//
//
//
//    private boolean isOccupied(DateTime startDate, DateTime endDate) throws IOException {
//
//
//        File dir = new File("Reservations/");
//        File[] directoryListing = dir.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//
//                if(pathname.getName().endsWith(".rez")){
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        for (File file : directoryListing) {
//
//            Reservation reservation = fromFile(file);
//
//                DateTime resStart = reservation.getStartDate();
//                DateTime resEnd = reservation.getEndDate();
//                Interval interval = new Interval(resStart, resEnd);
//
//                if(resStart != null && resEnd != null) {
//
//                    if (interval.contains(startDate) || interval.contains(endDate)) {
//                        return true;
//                    }
//                }
//            }
//
//
//        return false;
//
//    }
//
//
//
//    public boolean isItemReserved(String start, String end) throws IOException {
//
//        DateTime startDate = parseToDate(start, "dd/MM/yyyy");
//        DateTime endDate  = parseToDate(end, "dd/MM/yyyy");
//
//        if(isOccupied(startDate, endDate)){
//            return true;
//        }
//
//        return false;
//    }
//
//    public void changeReservation(Reservation reservation, String start, String end, User user) throws IOException, ReservationException {
//
//        delete(reservation);
//        Reservation res = create(user, start, end);
//        toFile(res);
//
//
//    }
//
//
//
//}
