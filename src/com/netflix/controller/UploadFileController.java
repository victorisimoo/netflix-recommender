package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ResourceBundle;

public class UploadFileController implements Initializable {
    private Principal principalScene;
    private List<String> files;
    private String filepath;
    @FXML
    private TextField txtNameFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        files = new ArrayList<>();
        files.add("*.csv");
    }

    public void setPrincipalScene(Principal principalScene) {
        this.principalScene = principalScene;
    }

    public Principal getPrincipalScene(){
        return this.principalScene;
    }

    public void uploadFile()  {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", files));
        File route = fc.showOpenDialog(null);
        if (route != null) {
            txtNameFile.setText(route.getAbsolutePath());
            filepath = route.getAbsolutePath();
        }
    }

    public void saveFile(){
        JSONArray ja = new JSONArray();
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        boolean flag = false;

        try (FileInputStream fis = new FileInputStream(filepath); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr) ){
            String line;
            int count =0;
            while ((line = reader.readLine()) != null) {
                flag = false;
                String[] values = line.split(",");
                for (String element : values)  {
                    if (element.equals("")) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    if (count != 0) {
                        var test = fillJson(values, ja);
                    } else {
                        count++;
                    }
                }
            }
            var value = getStringArray(ja);
            URL url = new URL("http://127.0.0.1:5000/upload_data/");
            for(String input: value) {
                byte[] postData = input.getBytes(StandardCharsets.UTF_8);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "utf-8");
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
            }

        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void profileScene(){
        principalScene.profileScene();
    }

    public String[] getStringArray(JSONArray jsonArray) {
        String[] stringArray = null;
        if (jsonArray != null) {
            int length = jsonArray.length();
            stringArray = new String[length];
            for (int i = 0; i < length; i++) {
                stringArray[i] = jsonArray.optString(i);
            }
        }
        return stringArray;
    }

    private JSONArray fillJson(String[] values, JSONArray ja) throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("color", values[0].trim());
        jo.put("director_name", values[1].trim());
        jo.put("num_critic_for_reviews", values[2].trim());
        jo.put("duration", values[3].trim());
        jo.put("director_facebook_likes", values[4].trim());
        jo.put("actor_3_facebook_likes", values[5].trim());
        jo.put("actor_2_name", values[6].trim());
        jo.put("actor_1_facebook_likes", values[7].trim());
        jo.put("gross", values[8].trim());
        jo.put("genres", values[9].trim());
        jo.put("actor_1_name", values[10].trim());
        jo.put("movie_title", values[11].trim());
        jo.put("num_voted_users", values[12].trim());
        jo.put("cast_total_facebook_likes", values[13].trim());
        jo.put("actor_3_name", values[14].trim());
        jo.put("facenumber_in_poster", values[15].trim());
        jo.put("plot_keywords", values[16].trim());
        jo.put("movie_imdb_link", values[17].trim());
        jo.put("num_user_for_reviews", values[18].trim());
        jo.put("language", values[19].trim());
        jo.put("country", values[20].trim());
        jo.put("content_rating", values[21].trim());
        jo.put("budget", values[22].trim());
        jo.put("title_year", values[23].trim());
        jo.put("actor_2_facebook_likes", values[24].trim());
        jo.put("imdb_score", values[25].trim());
        jo.put("aspect_ratio", values[26].trim());
        jo.put("movie_facebook_likes", values[27].trim());
        return ja.put(jo);
    }
}
