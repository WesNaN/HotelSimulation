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
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class FilesService implements DataService {

    @Override
    public void addUser(User user) throws ConnectionError {

        try {
            if(userExist(user)){
                throw new ConnectionError("User alredy exists", new Exception());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public Object fromFile(File f) throws ClassNotFoundException, IOException {

        InputStream file = null;
        try {
            file = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        return input.readObject();

    }

    public boolean userExist(User user) throws ConnectionError {

        ArrayList<User> users = (ArrayList<User>) getUsers();

        for(User u : users){
            if(u.userEquals(user)){
                return true;
            }
        }

        return false;
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

    public Collection<Reservation> getReservationsAsArray() throws IOException {

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

    public Collection<Room> getRoomsAsArray() throws IOException {

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

    public Collection<User> getUsers() throws ConnectionError{

        ArrayList<User> users = new ArrayList<User>();

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;

    }

    @Override
    public boolean numberExists(String number) throws ConnectionError {
        return false;
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
    public boolean userExists(User user) throws ConnectionError {
        return false;
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
