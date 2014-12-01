package main;

import service.ConnectionError;
import service.DataService;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private long user_id;
    private final long UNIQUE_ID;


    public User(String name, long unique_id, DataService service) throws ConnectionError {

        if(!service.isConnected()){
            service.connect();
        }

        this.user_id = service.getUserID();
        this.name = name;
        UNIQUE_ID = unique_id;

        service.addUser(this);

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