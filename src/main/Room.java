package main;

import service.ConnectionError;
import service.DataService;

import java.io.Serializable;

public class Room implements Serializable{

    private long room_id;
    private double priceForNight;
    private int rooms;
    private int beds;

    public Room(int rooms, int beds, double price, DataService service) throws ConnectionError {

        if(!service.isConnected()){
            service.connect();
        }

        this.rooms = rooms;
        this.beds = beds;
        this.priceForNight = price;
        room_id = service.getRoomID();

        service.saveRoom(this);

    }

    public double getPriceForNight() {
        return priceForNight;
    }

    public int getRooms() {
        return rooms;
    }

    public int getBeds() {
        return beds;
    }

    public long getRoomID(){
        return room_id;
    }

}
