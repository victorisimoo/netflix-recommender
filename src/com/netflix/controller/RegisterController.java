package com.netflix.controller;

import com.netflix.app.ParameterStringBuilder;
import com.netflix.app.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Principal principalScene;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    public Principal getPrincipalScene(){
        return principalScene;
    }

    public void loginUser(){ principalScene.homeScene();}

    public void createUser(){
        String user = txtEmail.getText();
        String pass = txtPassword.getText();
        if(api_insertUser(user, pass) == 200){
            loginUser();
        } // No se hizo correctamente el registro del usuario
    }

    public int api_insertUser(String username, String password) {
        return 200;
    }
}
