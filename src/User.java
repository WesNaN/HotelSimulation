import java.io.Serializable;
import java.sql.SQLException;

public class User implements Serializable {

    private String name;
    private long user_id;
    private final long UNIQUE_ID;


    public User(String name, long unique_id) throws SQLException, ClassNotFoundException {

        if(!Simulation.ds.isConnected()){
            Simulation.ds.connect();
        }

        this.user_id = Oracle.getUserID();
        this.name = name;
        UNIQUE_ID = unique_id;

        Simulation.ds.addUser(this);

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getName() {
        return name;
    }

    public long getUserID() {
        return user_id;
    }

    public long getUNIQUE_ID() {
        return UNIQUE_ID;
    }



}