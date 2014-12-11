package main;

import service.ConnectionError;
import service.DataService;
import service.NumbersService;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String user_id;

    private String number;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean userEquals(User user){

        if(user.getName() == name || user.getUserID() == user_id){
            return true;
        }
        return false;
    }

    public User(String name, DataService service) throws ConnectionError {
        this.name = name;
        user_id = NumbersService.createNumberForUser(this);
    }


    public String getName() {
        return name;
    }

    public String getUserID() {
        return user_id;
    }
}