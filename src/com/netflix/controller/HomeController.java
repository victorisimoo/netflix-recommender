package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * Class to handle user login
 * @author victorisimoo
 */
public class HomeController implements Initializable {

    private Principal principalScene;
    private String process = null;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtEmail;
    @FXML private Pane infError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infError.setVisible(false);
    }
    
    /**
     * Method to handle user login
     */
    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    /**
     * Method to log in to the user registry
     */
    public void createAccount(){ principalScene.registerScene();}

    /**
     * Method to log in to the user registry
     */
    public void loginUserMethod() throws IOException {
        String user = txtEmail.getText();
        String pass = txtPassword.getText();
        api_loginUser(user, pass);
        if(process != null){
            principalScene.profileScene();
        }else{
            infError.setVisible(true);
            txtEmail.setText("");
            txtPassword.setText("");
        }
    }

    /**
     * Method for error handling
     */
    public void errorMessage(){
        infError.setVisible(false);
    }

    /**
     * Method for login process
     */
    public void api_loginUser(String username, String password) throws IOException {
        HttpURLConnection conn = null;
        DataOutputStream os;

        try{
            URL url = new URL("http://127.0.0.1:5000/login/"); //important to add the trailing slash after add
            String[] inputData = {"{\"name\": "+(char)34 + username + (char)34+", \"contrasena\": "+(char)34 + password + (char)34+"}"};
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
                    if(output.equals("none")){
                        process = null;
                    }else {
                        process = output;
                    }
                }
                conn.disconnect();
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
