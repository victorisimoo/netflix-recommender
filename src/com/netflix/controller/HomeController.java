package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

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
}
