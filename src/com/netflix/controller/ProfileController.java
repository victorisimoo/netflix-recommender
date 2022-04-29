package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private Principal principalScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }
}
