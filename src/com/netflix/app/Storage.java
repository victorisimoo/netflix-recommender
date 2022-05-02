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
    public String password_actual_user = "";
    public String name_actual_movie = "";


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

    public void setName_actual_user(String name_actual_user) {
        this.name_actual_user = name_actual_user;
    }

    public String getPassword_actual_user() {
        return password_actual_user;
    }
    public void setName_actual_movie(String name_actual_movie) {
        this.name_actual_movie = name_actual_movie;
    }

    public String getName_actual_movie() {
        return name_actual_movie;
    }

    public void setPassword_actual_user(String password_actual_user) {
        this.password_actual_user = password_actual_user;
    }
}
