import java.sql.Date;
import java.sql.SQLException;

public class Reservation{

    private Date startDate;
    private Date endDate;
    private long room_id;
    private long res_id;
    private User user;

    public Reservation(User user, Date start, Date end, long room_id) throws SQLException, ClassNotFoundException {

        if(!Simulation.ds.isConnected()){
            Simulation.ds.connect();
        }

        res_id = Oracle.getResID();
        this.user = user;
        this.startDate = start;
        this.endDate = end;
        this.room_id = room_id;

        Simulation.ds.saveReservation(this);

    }






    @Override
    public String toString(){
        return this.user.getName() + "_" + "_" + res_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getRoomID() {
        return room_id;
    }

    public long getResID() {
        return res_id;
    }

    public User getUser() {
        return user;
    }
}
