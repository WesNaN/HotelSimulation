package main;

import service.DataService;

public class Application {

    DataService ds;

    public Application(DataService ds){
        this.ds = ds;
    }

    public DataService getDataService() {
        return ds;
    }
}
