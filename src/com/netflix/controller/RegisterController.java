package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Principal principalScene;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Sets the scene
     *
     * @param principalScene
     */
    public void setPrincipalScene(Principal principalScene) {
        this.principalScene = principalScene;
    }

    /**
     * Register a new user
     */
    public void registerUser() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        HttpURLConnection conn = null;
        DataOutputStream os;

        try{
            URL url = new URL("http://127.0.0.1:5000/create_user/");
            String[] inputData = {"{\"name\": "+(char)34 + email + (char)34+", \"contrasena\": "+(char)34 + password + (char)34+"}"};
            for(String input: inputData){
                byte[] postData = input.getBytes(StandardCharsets.UTF_8);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(input.length()));
                os = new DataOutputStream(conn.getOutputStream());
                os.write(postData);
                os.flush();

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                conn.disconnect();
                principalScene.homeScene();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally  {
            if(conn != null)  {
                conn.disconnect();
            }
        }
    }
}