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

public class SearchController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private ArrayList<Movie> moviesSearch = new ArrayList<>();
    private Principal principalScene;
    String search;
    String typeSearch;
    String output = null;
    @FXML private ComboBox cmbTypeSearch;
    @FXML private TextArea txtResult;
    @FXML private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtResult.setEditable(false);
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

    /**
     * Method returning to principal screen
     * @param principalScene
     */
    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    /**
     * Method returning to home screen
     */
    public void homeScene(){
        this.principalScene.profileScene();
    }

    /***
     * Method that performs the search for movies in the database.
     * @param search
     * @param typeSearch
     * @throws MalformedURLException
     */
    public void search_database(String search, int typeSearch) throws MalformedURLException {
        HttpURLConnection conn = null;
        DataOutputStream os;
        URL url = null;
        String[] inputData = null;
        int count = 0;
        Movie movie = null;

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
                    output = "{\"movies\" : "+ output +" }";
                    JSONObject object = new JSONObject(output);
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
                        movie.setTitle((aux[21].substring(aux[21].indexOf(":") + 1, aux[21].length() - 1)).replace("\"", ""));
                        movie.setDirector((aux[25].substring(aux[25].indexOf(":") + 1, aux[25].length() - 1)).replace("\"", ""));
                        movie.setPoints((aux[8].substring(aux[8].indexOf(":") + 1, aux[8].length() - 1)).replace("\"", ""));
                        moviesSearch.add(movie);
                    }
                    setMoviesSearch();
                }
                count++;
                conn.disconnect();
            }
            setMoviesSearch();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally  {
            if(conn != null)  {
                conn.disconnect();
            }
        }
    }

    public void setMoviesSearch(){
        String movies = "";
        for(Movie movies_cast: moviesSearch){
            movies = movies + movies_cast.toString() + '\n';
        }
        txtResult.setText(movies);
    }

    public void searchSearchView() throws MalformedURLException {
        txtResult.clear();
        moviesSearch = new ArrayList<>();
        String search = txtSearch.getText();
        String typeSearch = cmbTypeSearch.getValue().toString();
        if (typeSearch.equals("Título")) {
            search_database(search, 0);
        } else if (typeSearch.equals("Género")) {
            search_database(search, 1);
        } else {
            search_database(search, 2);
        }
    }

}
