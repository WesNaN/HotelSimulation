public interface DataService {

    public void addUser(User user) throws ConnectionError;
    public void saveReservation(Reservation reservation) throws ConnectionError;
    public void saveRoom(Room room) throws ConnectionError;
    public boolean isConnected();
    public void connect();
    public long getResID() throws ConnectionError;
    public long getRoomID() throws ConnectionError;
    public long getUserID() throws ConnectionError;
}
