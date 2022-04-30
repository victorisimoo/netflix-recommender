package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to handle user login
 * @author victorisimoo
 */
public class HomeController implements Initializable {
    private Principal principalScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    public Principal getPrincipalScene(){
        return principalScene;
    }

    public void createAccount(){ principalScene.registerScene();}

    public void uploadFile(){
        principalScene.uploadFileScene();
    }

    /*public void loginUser() throws IOException {
        URL url_loginUser = new URL("http://127.0.0.1:5000/LoginUser");
        HttpURLConnection connection = (HttpURLConnection) url_loginUser.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status = connection.getResponseCode();
        System.out.println(status);
    }*/
}
