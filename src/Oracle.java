import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class Oracle implements DataService {

    public static Connection conn;

    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    public void connect() throws SQLException {

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

    public boolean isConnected() throws SQLException {
        if (conn.isValid(10) || conn.isClosed()){
            return false;
        }
        return true;
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {

        if(!isConnected()){
            connect();
        }

        //First check if user exists
        PreparedStatement checkStatment = conn.prepareStatement(
                "SELECT * FROM Users"
        );
        ResultSet users = checkStatment.executeQuery();

        while(users.next()){
            if(users.getInt(1) == user.getUserID()){
                LOGGER.info("User already exists!");
                return;
            }
        }

        PreparedStatement insertStatment = conn.prepareStatement(
                "INSERT INTO Users(user_id, name)" +
                    "VALUES(?,?)"
        );
        insertStatment.setLong(1, user.getUserID());
        insertStatment.setString(2, user.getName());
        insertStatment.executeQuery();

    }

    public void saveReservation(Reservation reservation) throws SQLException, ClassNotFoundException {

        if(!isConnected()){
            connect();
        }

        // check if reservation already exists
        PreparedStatement checkStatement = conn.prepareStatement(
                "SELECT * FROM Reservations"
        );
        ResultSet resSet = checkStatement.executeQuery();

        while (resSet.next()){
            if (resSet.getInt(1) == reservation.getResID()){
                syncReservation(reservation);
                return;
            }
        }

        // if dates fit to room
        PreparedStatement roomStatement = conn.prepareStatement(
            "SELECT * FROM Rooms"
        );
        ResultSet roomSet = roomStatement.executeQuery();

        // Creating
        PreparedStatement prst = conn.prepareStatement(
                "INSERT INTO Reservations(res_id, start_date, end_date, user_id, status, room_id)" +
                        "VALUES(?,?,?,?,?,?)"
        );
        prst.setLong(1, reservation.getResID());
        prst.setDate(2, reservation.getStartDate());
        prst.setDate(3, reservation.getEndDate());
        prst.setLong(4, reservation.getUser().getUserID());
        prst.setBoolean(5, true);
        prst.setLong(6, reservation.getRoomID());
        prst.executeQuery();


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


    public  void saveRoom(Room room) throws SQLException, ClassNotFoundException {

        if(!isConnected()){
            connect();
        }

        // Check if room exists
        PreparedStatement checkStatment = conn.prepareStatement(
                "SELECT * FROM Rooms"
        );
        ResultSet rooms_list = checkStatment.executeQuery();

        // ... if so sync it
        while (rooms_list.next()){
            if (rooms_list.getInt(1) == room.getRoomID()){
                syncRoom(room.getRoomID(), room.getPriceForNight(), room.getBeds(), room.getRooms());
                return;
            }
        }

        // ... if not so create
        PreparedStatement insertStatment = conn.prepareStatement(
                "INSERT INTO Rooms(room_id, price, beds, rooms) " +
                        "VALUES (?,?,?,?)"
        );
        insertStatment.setLong(1, room.getRoomID());
        insertStatment.setDouble(2, room.getPriceForNight());
        insertStatment.setInt(3, room.getBeds());
        insertStatment.setInt(4, room.getRooms());
        insertStatment.executeQuery();
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

    public static long getUserID() throws SQLException {

        PreparedStatement prst = conn.prepareStatement(
                "SELECT seqUser.nextval from dual"
        );
        ResultSet set = prst.executeQuery();
        set.next();
        return set.getLong(1);
    }

    public static long getRoomID() throws SQLException {

        PreparedStatement prst = conn.prepareStatement(
                "SELECT room_seq.nextval from dual"
        );
        ResultSet set = prst.executeQuery();
        set.next();
        return set.getLong(1);
    }

    public static long getResID() throws SQLException {

        PreparedStatement prst = conn.prepareStatement(
                "SELECT res_seq.nextval from dual"
        );
        ResultSet set = prst.executeQuery();
        set.next();
        return set.getLong(1);
    }

    public void canConnect(){

    }
}
