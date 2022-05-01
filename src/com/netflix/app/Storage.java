package com.netflix.app;

/**
 * Class for managing user sessions
 * @author victorisimoo
 */
public class Storage {

    public Storage(){}
    private static Storage _instance = null;
    public String[] actualSearch = new String[2];
    public String name_actual_user = "";


    public static Storage getInstance(){
        if(_instance == null)
            _instance = new Storage();
        return  _instance;
    }

    public String getName_actual_user() {
        return name_actual_user;
    }

    public String[] getActualSearch() {
        return actualSearch;
    }

    public void setActualSearch(String[] actualSearch) {
        this.actualSearch = actualSearch;
    }
}
