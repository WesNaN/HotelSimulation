package service;

public class ConnectionError extends Exception{

    public ConnectionError(String msg, Exception e){
        e.printStackTrace();
    }

}
