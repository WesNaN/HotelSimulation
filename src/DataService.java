import java.sql.SQLException;

public interface DataService {

    public void addUser(User user) throws SQLException, ClassNotFoundException;
    public void saveReservation(Reservation reservation) throws SQLException, ClassNotFoundException;
    public void saveRoom(Room room) throws SQLException, ClassNotFoundException;
    public boolean isConnected() throws SQLException;
    public void connect() throws SQLException;



}
