package service.db;

import main.Reservation;
import main.Room;
import main.User;
import service.ConnectionError;
import service.DataService;
import service.NumbersService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Logger;

public class Oracle implements DataService {

    public static Connection conn;

    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    public void connect(){

        LOGGER.warning("Not connected to database!");
        LOGGER.info("So I will try to connect...");

        boolean success = false;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String hostname = "oracledb";
            int port = 1521;
            String database = "XE";
            String user = "system";
            String password = "tranlaHaa1";

            String url = "jdbc:oracle:thin:@//" + hostname + "/" +database;

            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            //props.setProperty("ssl", "true");

            conn = DriverManager.getConnection(url, props);
            success = true;

        } catch (ClassNotFoundException e) {
            LOGGER.warning("Driver not found!");
            e.printStackTrace();
            return;
        } catch (SQLException e){
            LOGGER.warning("Cannot establish connection!");
            e.printStackTrace();
            return;
        } finally {
            if(success){
                LOGGER.fine("Connection successful!");
            } else {
                LOGGER.warning("Connection failure!");
            }

        }
    }

    public void disconnect() throws SQLException {
        conn.close();
    }

    public boolean isConnected(){

        if(conn == null){
            return false;
        }

        try {
            if (conn.isValid(10) || conn.isClosed()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addUser(User user) throws ConnectionError {

        if(!isConnected()){
            connect();
        }

        //First check if user exists
        PreparedStatement checkStatment = null;
        try {
            checkStatment = conn.prepareStatement(
                    "SELECT 1 FROM Users u Where u.NUMER=?"
            );
            checkStatment.setString(1, user.getUserID());
        } catch (SQLException e) {
            throw new ConnectionError("Cannot prepare a SQL statement", e);
        }
        ResultSet usersResponse = null;
        int a = -1;
        try {
            usersResponse = checkStatment.executeQuery();
            a = usersResponse.getFetchSize();

        } catch (SQLException e) {
            throw new ConnectionError("Cannot execute a SQL statement", e);
        }

        if(a == 1){
            LOGGER.warning("User already exists!");
            return;
        }
        if(a == -1){
            LOGGER.warning("Error while trying to add user");
            throw new ConnectionError("Really unexpected error!", new SQLException());
        }

        PreparedStatement insertStatment = null;
        try {
            insertStatment = conn.prepareStatement(
                    "INSERT INTO Users(id, name, numer)" +
                        "VALUES(?,?,?)"
            );
            insertStatment.setLong(1, getUserID());
            insertStatment.setString(2, user.getName());
            insertStatment.setString(3, NumbersService.createNumberForUser(user));
            insertStatment.executeQuery();

        } catch (SQLException e) {
            throw new ConnectionError("Cannot insert records into table", e);
        }

    }

    public void saveReservation(Reservation reservation) throws ConnectionError {

        if(!isConnected()){
            connect();
        }

        // check if reservation already exists
        PreparedStatement checkStatement = null;
        try {
            checkStatement = conn.prepareStatement(
                    "SELECT * FROM Reservations WHERE id=" + reservation.getResID()
            );
        } catch (SQLException e) {
            throw new ConnectionError("Cannot prepare a SQL statement", e);
        }
        ResultSet resSet = null;
        int a = -1;
        try {
            resSet = checkStatement.executeQuery();
            a = resSet.getFetchSize();
        } catch (SQLException e) {
            throw new ConnectionError("Cannot execute a SQL statement", e);
        }

        if(a == 1){
            try {
                syncReservation(reservation);
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // if dates fit to room
        PreparedStatement roomStatement = null;
        try {
            roomStatement = conn.prepareStatement(
                "SELECT * FROM Rooms"
            );
        } catch (SQLException e) {
            throw new ConnectionError("Cannot prepare a SQL statement", e);
        }
        try {
            ResultSet roomSet = roomStatement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("Cannot execute a SQL statement", e);
        }

        // Creating
        PreparedStatement prst = null;
        try {
            prst = conn.prepareStatement(
                    "INSERT INTO Reservations(res_id, start_date, end_date, user_id, status, room_id)" +
                            "VALUES(?,?,?,?,?,?)"
            );
            prst.setLong(1, reservation.getResID());
            prst.setDate(2, reservation.getStartDate());
            prst.setDate(3, reservation.getEndDate());
            prst.setLong(4, getCurrentUserID(reservation.getUser()));
            prst.setBoolean(5, true);
            prst.setLong(6, reservation.getRoomID());
            prst.executeQuery();

        } catch (SQLException e) {
            throw new ConnectionError("Cannot insert records into table", e);
        }
    }


    public static void syncReservation(Reservation reservation) throws SQLException {

        PreparedStatement prst = conn.prepareStatement(
                "UPDATE Reservation " +
                "SET start_date = ?, " +
                "end_date = ?" +
                "WHERE res_id = ?;"
        );
        prst.setDate(1, reservation.getStartDate());
        prst.setDate(2, reservation.getEndDate());
        prst.setLong(3, reservation.getResID());

    }

    public  void saveRoom(Room room) throws ConnectionError {

        if(!isConnected()){
            connect();
        }

        // Check if room exists
        PreparedStatement checkStatment;
        ResultSet rooms_list;
        try {
            checkStatment = conn.prepareStatement(
                    "SELECT * FROM Rooms WHERE id=" + room.getRoomID()
            );
            rooms_list = checkStatment.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("Cannot prepare and execute a SQL statement", e);
        }


        int a = -1;
        try {
            a = rooms_list.getFetchSize();
            if(a == 1) {
                syncRoom(room.getRoomID(), room.getPriceForNight(), room.getBeds(), room.getRooms());
                return;
            }
        } catch (SQLException e) {
            throw new ConnectionError("Cannot check if room already exists", e);
        }

        // ... if not so create
        PreparedStatement insertStatment = null;
        try {
            insertStatment = conn.prepareStatement(
                    "INSERT INTO Rooms(room_id, price, beds, rooms) " +
                            "VALUES (?,?,?,?)"
            );
            insertStatment.setLong(1, room.getRoomID());
            insertStatment.setDouble(2, room.getPriceForNight());
            insertStatment.setInt(3, room.getBeds());
            insertStatment.setInt(4, room.getRooms());
            insertStatment.executeQuery();

        } catch (SQLException e) {
            throw new ConnectionError("Cannot insert records into table", e);
        }
    }


    public static void syncRoom(long room_id, double price, int beds, int rooms) throws SQLException {

        PreparedStatement insertStatment = conn.prepareStatement(
                "UPDATE Rooms " +
                        "SET price = ?," +
                            "beds = ?," +
                            "rooms = ?" +
                        "WHERE room_id = ?"
        );
        insertStatment.setDouble(1, price);
        insertStatment.setInt(2, beds);
        insertStatment.setInt(3, rooms);
        insertStatment.setLong(4, room_id);
        insertStatment.executeQuery();

    }



    public void cleanDatabase() throws SQLException, ClassNotFoundException {

        if(!isConnected()){
            connect();
        }

        conn.createStatement().execute("DELETE FROM Reservations");
        conn.createStatement().execute("DELETE FROM Users");
        conn.createStatement().execute("DELETE FROM Rooms");

        conn.commit();
    }

    public long getUserID() throws ConnectionError{

        PreparedStatement prst = null;
        try {
            prst = conn.prepareStatement(
                    "SELECT seqUser.nextval from dual"
            );
            ResultSet set = prst.executeQuery();
            set.next();
            return set.getLong(1);

        } catch (SQLException e) {
            throw new ConnectionError("SQL error while trying to get UserID", e);
        }
    }

    @Override
    public Collection<User> getUsers() throws ConnectionError{
        ArrayList<User> users = new ArrayList<User>();

        PreparedStatement usersStatement;
        ResultSet usersSet = null;
        try {
            usersStatement = conn.prepareStatement(
                    "SELECT * FROM Users"
            );
            usersSet = usersStatement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("Cannot get users from database!", e);
        }

        try {
            while(usersSet.next()){
                User u = new User(usersSet.getString(2), this);
                u.setUser_id(usersSet.getString(3));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean numberExists(String number) throws ConnectionError {

        if(number.equals("")){
            return true;
        }

        PreparedStatement stmt;
        ResultSet set;
        try {
            stmt = conn.prepareStatement(
                    "SELECT numer FROM USERS"
            );
            set = stmt.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("Cannot list unique nubmers from database!", e);
        }

        try {
            while(set.next()){
                if(set.getString(3).equals(number)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }


    @Override
    public boolean userExists(User user) throws ConnectionError{

        if(!isConnected()){
            connect();
        }

        PreparedStatement usersStatement = null;
        ResultSet usersSet = null;
        try {
            usersStatement = conn.prepareStatement(
                    "SELECT 1 FROM Users WHERE " +
                            "UPPER(Name) = UPPER(?)"
            );
            usersStatement.setString(1, user.getName());
            usersSet = usersStatement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("SQL error while getting users from database", e);
        }

        try {
            if(usersSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public long getRoomID() throws ConnectionError{

        PreparedStatement prst = null;
        ResultSet set = null;
        try {
            prst = conn.prepareStatement(
                    "SELECT room_seq.nextval from dual"
            );
            set = prst.executeQuery();
            set.next();
            return set.getLong(1);

        } catch (SQLException e) {
            throw new ConnectionError("SQL error while trying to get RoomID", e);
        }
    }

    public long getResID() throws ConnectionError {

        PreparedStatement prst = null;
        ResultSet set = null;
        try {
            prst = conn.prepareStatement(
                    "SELECT res_seq.nextval from dual"
            );
            set = prst.executeQuery();
            set.next();

            return set.getLong(1);

        } catch (SQLException e) {
            throw new ConnectionError("SQL error while trying to get ResID", e);
        }
    }

    public long getCurrentUserID(User user) throws ConnectionError {

        PreparedStatement usersStatement = null;
        ResultSet usersSet = null;
        try {
            usersStatement = conn.prepareStatement(
                    "SELECT 1 FROM Users WHERE Name = ?"
            );
            usersStatement.setString(1, user.getName());
            usersSet = usersStatement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectionError("SQL error while getting users from database", e);
        }

        try {
            if(usersSet.getFetchSize() == 1){
                return usersSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


}
