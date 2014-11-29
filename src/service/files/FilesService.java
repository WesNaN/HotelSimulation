package service.files;

import main.Reservation;
import main.Room;
import main.User;
import service.ConnectionError;
import service.DataService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FilesService implements DataService {

    @Override
    public void addUser(User user) throws ConnectionError {

        File temp = new File("Data/Users/" + user.toString());
        if(!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            throw new ConnectionError("Cannot find file",  e);
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fout);
        } catch (IOException e) {
            throw new ConnectionError("Cannot write to file", e);
        }
        if (oos != null) {
            try {
                oos.writeObject(user);
            } catch (IOException e) {
                throw new ConnectionError("Cannot write to file", e);
            }
        }
    }

    @Override
    public void saveReservation(Reservation reservation) throws ConnectionError{

        File temp = new File("Data/Reservations/" + reservation.toString());
        if(!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            throw new ConnectionError("Cannot find file",  e);
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fout);
        } catch (IOException e) {
            throw new ConnectionError("Cannot write to file", e);
        }
        if (oos != null) {
            try {
                oos.writeObject(reservation);
            } catch (IOException e) {
                throw new ConnectionError("Cannot write to file", e);
            }
        }

    }

    @Override
    public void saveRoom(Room room) throws ConnectionError {

        File temp = new File("Data/Rooms/" + room.toString());
        if(!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            throw new ConnectionError("Cannot find file",  e);
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fout);
        } catch (IOException e) {
            throw new ConnectionError("Cannot write to file", e);
        }
        if (oos != null) {
            try {
                oos.writeObject(room);
            } catch (IOException e) {
                throw new ConnectionError("Cannot write to file", e);
            }
        }

    }

    public Object objectFromFile(Path file) throws IOException, ClassNotFoundException {

        FileInputStream fin = new FileInputStream(file.toFile());
        ObjectInputStream ois = new ObjectInputStream(fin);
        return ois.readObject();

    }

    public ArrayList<Reservation> getReservationsAsArray() throws IOException {

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        Files.walk(Paths.get("Data/Reservations/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    reservations.add((Reservation) objectFromFile(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        return reservations;
    }

    public ArrayList<Room> getRoomsAsArray() throws IOException {

        ArrayList<Room> rooms = new ArrayList<Room>();

        Files.walk(Paths.get("Data/Rooms/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    rooms.add((Room) objectFromFile(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return rooms;

    }

    public ArrayList<User> getUsersAsArray() throws IOException {

        ArrayList<User> users = new ArrayList<User>();

        Files.walk(Paths.get("Data/Users/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    users.add((User) objectFromFile(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return users;

    }

    @Override
    public boolean isConnected(){
        return true;
    }

    @Override
    public void connect(){

    }

    @Override
    public long getResID() throws ConnectionError {
        AtomicInteger seq = new AtomicInteger();
        return seq.incrementAndGet();

    }

    @Override
    public long getRoomID() throws ConnectionError {
        AtomicInteger seq = new AtomicInteger();
        return seq.incrementAndGet();
    }

    @Override
    public long getUserID() throws ConnectionError {
        AtomicInteger seq = new AtomicInteger();
        return seq.incrementAndGet();
    }
}
