package com.netflix.controller;

import com.netflix.app.Principal;
import com.netflix.app.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private Principal principalScene;
    String search;
    String typeSearch;
    String output = null;

    @FXML
    private ComboBox cmbTypeSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeSearch = Storage.getInstance().getActualSearch()[0];
        search = Storage.getInstance().getActualSearch()[1];
        try {
            if (typeSearch.equals("Título")) {
                search_database(search, 0);
            } else if (typeSearch.equals("Género")) {
                search_database(search, 1);
            } else {
                search_database(search, 2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        cmbTypeSearch.setItems(searchType);
    }

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    public void homeScene(){
        this.principalScene.profileScene();
    }

    public void search_database(String search, int typeSearch) throws MalformedURLException {
        HttpURLConnection conn = null;
        DataOutputStream os;
        URL url = null;
        String[] inputData = null;

        if (typeSearch == 0){
            url = new URL("http://127.0.0.1:5000/search_title/"); //important to add the trailing slash after add
            inputData = new String[]{"{\"movie_title\": " + (char) 34 + search + (char) 34 + "}"};
        }else if(typeSearch == 1){
            url = new URL("http://127.0.0.1:5000/search_genre/"); //important to add the trailing slash after add
            inputData = new String[]{"{\"genres\": "+(char)34 + search + (char)34+"}"};
        }else{
            url = new URL("http://127.0.0.1:5000/search_director/"); //important to add the trailing slash after add
            inputData = new String[]{"{\"director_name\": "+(char)34 + search + (char)34+"}"};
        }

        try{
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

                while ((output = br.readLine()) != null) {
                    System.out.println(output);
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
