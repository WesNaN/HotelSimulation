import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class FilesService implements DataService{

    final String path = "Reservations/";

    @Override
    public void addUser(User user) throws SQLException{

        FileOutputStream fout = new FileOutputStream(path + user.toString());
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(user);


    }

    @Override
    public void saveReservation(Reservation reservation) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void saveRoom(Room room) throws SQLException, ClassNotFoundException {

    }

    @Override
    public boolean isConnected() throws SQLException {
        return false;
    }

    @Override
    public void connect() throws SQLException {

    }
}
