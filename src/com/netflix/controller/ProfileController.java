package com.netflix.controller;

import com.netflix.app.Principal;
import com.netflix.app.Storage;
import com.netflix.bean.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class ProfileController
 * @author victorisimo
 */
public class ProfileController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private ArrayList<Movie> moviesView = new ArrayList<>();
    private ArrayList<Movie> moviesPrediction = new ArrayList<>();
    private Principal principalScene;
    @FXML private ComboBox cmbTypeSearch;
    @FXML private TextField txtSearch;
    @FXML private TextArea pnView;
    @FXML private TextArea pnPrediction;
    String outputView = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTypeSearch.setItems(searchType); //Set the items of the combobox
        moviesView = new ArrayList<>();
        moviesPrediction = new ArrayList<>();
        getViewsProfile();
        getRecommendations();
    }

    /**
     *
     * @param principalScene
     */
    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    /**
     * Method searchScene
     */
    public void searchScene(){
        this.principalScene.searchScene();
    }

    /**
     * Method search
     */
    public void searchOption(){
        if(txtSearch.getText() != "" && cmbTypeSearch.getValue() != null){
            String actualSeach[] = new String[2];
            actualSeach[0] = cmbTypeSearch.getValue().toString();
            actualSeach[1] = txtSearch.getText();
            Storage.getInstance().setActualSearch(actualSeach);
            searchScene();
        }
    }

    public void logOut(){
        this.principalScene.homeScene();
        moviesPrediction.clear();
        moviesView.clear();
        Storage.getInstance().setName_actual_movie(null);
        Storage.getInstance().setPassword_actual_user(null);

    }

    public void getRecommendations(){
        HttpURLConnection conn = null;
        DataOutputStream os;
        int count = 0;
        Movie movie = null;
        try{
            URL url = new URL("http://127.0.0.1:5000/recommendations/");
            String[] inputData = {"{\"name\": "+(char)34 + Storage.getInstance().name_actual_user + (char)34+", \"contrasena\": "+(char)34 + Storage.getInstance().getPassword_actual_user() + (char)34+"}"};
            byte[] postData = inputData[0].getBytes(StandardCharsets.UTF_8);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(inputData[0].length()));
            os = new DataOutputStream(conn.getOutputStream());
            os.write(postData);
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((outputView = br.readLine()) != null) {
                outputView = "{\"movies\" : "+ outputView +" }";
                JSONObject object = new JSONObject(outputView);
                JSONArray array = object.getJSONArray("movies");
                ArrayList<Object> data = new ArrayList<Object>();
                if(array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        data.add(array.get(i));
                    }
                }
                for(int i=0; i < data.size(); i++) {
                    movie = new Movie();
                    movie.setIndex(i);
                    String[] aux = data.get(i).toString().split(",");
                    movie.setTitle((aux[3].substring(aux[3].indexOf(":") + 1, aux[3].length() - 1)).replace("\"", ""));
                    movie.setDirector((aux[2].substring(aux[2].indexOf(":") + 1, aux[2].length() - 1)).replace("\"", ""));
                    movie.setPoints((aux[4].substring(aux[4].indexOf(":") + 1, aux[4].length() - 1)).replace("\"", ""));
                    moviesPrediction.add(movie);
                }
                setMoviesRecommendation();
            }
            count++;
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getViewsProfile(){
        HttpURLConnection conn = null;
        DataOutputStream os;
        int count = 0;
        Movie movie = null;
        try{
            URL url = new URL("http://127.0.0.1:5000/views/");
            String[] inputData = {"{\"name\": "+(char)34 + Storage.getInstance().name_actual_user + (char)34+", \"contrasena\": "+(char)34 + Storage.getInstance().getPassword_actual_user() + (char)34+"}"};
            byte[] postData = inputData[0].getBytes(StandardCharsets.UTF_8);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(inputData[0].length()));
            os = new DataOutputStream(conn.getOutputStream());
            os.write(postData);
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((outputView = br.readLine()) != null) {
                outputView = "{\"movies\" : "+ outputView +" }";
                JSONObject object = new JSONObject(outputView);
                JSONArray array = object.getJSONArray("movies");
                ArrayList<Object> data = new ArrayList<Object>();
                if(array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        data.add(array.get(i));
                    }
                }
                for(int i=0; i < data.size(); i++) {
                    movie = new Movie();
                    movie.setIndex(i);
                    String[] aux = data.get(i).toString().split(",");
                    movie.setTitle((aux[2].substring(aux[2].indexOf(":") + 1, aux[2].length() - 1)).replace("\"", ""));
                    movie.setDirector((aux[1].substring(aux[1].indexOf(":") + 1, aux[1].length() - 1)).replace("\"", ""));
                    movie.setPoints((aux[3].substring(aux[3].indexOf(":") + 1, aux[3].length() - 1)).replace("\"", ""));
                    moviesView.add(movie);
                }
                setMoviesView();
            }
            count++;
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setMoviesView(){
        pnView.clear();
        String movies = "";
        for(Movie movies_cast: moviesView){
            movies = movies + movies_cast.getWithGenre() + '\n';
        }
        pnView.setText(movies);
    }

    public void setMoviesRecommendation(){
        int count = 0;
        pnPrediction.clear();
        String movies = "";
        for(Movie movies_cast: moviesPrediction){
            if(count < 20){
                movies = movies + movies_cast.getWithGenre() + '\n';
            }
            count++;
        }
        pnPrediction.setText(movies);
    }
}
