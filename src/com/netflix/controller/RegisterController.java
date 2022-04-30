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
    @FXML private Button btnRegister;

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

        try{
            // Establishment of connection with the api rest
            URL url_insertUser = new URL("http://127.0.0.1:5000/insertUser");
            HttpURLConnection con = (HttpURLConnection) url_insertUser.openConnection();
            con.setRequestMethod("POST");
            int status = con.getResponseCode();
            // Structuring the request
            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("name", username);
            parameters.put("contrasena", password);

            StringBuilder post_data = new StringBuilder();
            for(Map.Entry param : parameters.entrySet()){
                if(post_data.length() != 0) post_data.append('&');
                post_data.append(URLEncoder.encode(String.valueOf(param.getKey()), "UTF-8"));
                post_data.append('=');
                post_data.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = post_data.toString().getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes);
            status = con.getResponseCode();
            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();
            System.out.println(response);
            in.close();
            con.disconnect();
            return 200;
        }catch(MalformedURLException malformedURLException){
            malformedURLException.printStackTrace();
            return 400;
        }catch (IOException ioException){
            ioException.printStackTrace();
            return 400;
        }
    }
}
