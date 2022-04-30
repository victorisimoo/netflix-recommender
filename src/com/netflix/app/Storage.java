package com.netflix.app;

/**
 * Class for managing user sessions
 * @author victorisimoo
 */
public class Storage {

    private static Storage _instance = null;

    public static Storage getInstance(){
        if(_instance == null)
            _instance = new Storage();
        return  _instance;
    }
}
