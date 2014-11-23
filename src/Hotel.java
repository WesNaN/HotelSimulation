import java.util.ArrayList;

public class Hotel {

    String name;
    ArrayList<Room> rooms = new ArrayList<Room>();

    public Hotel(String name){
        this.name = name;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public Hotel(String name, int floors, ArrayList<Room> rooms){
        this.name = name;

        if(rooms != null){
            this.rooms = rooms;
        }

    }


    public void addRoom(Room room){
        rooms.add(room);
    }



}
