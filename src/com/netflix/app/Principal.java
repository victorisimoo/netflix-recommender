package com.netflix.app;

import com.netflix.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author victorisimoo
 *
 *
 */
public class Principal extends Application {

    private final String VIEWS_PACKAGES = "/com/netflix/view/";
    private Stage principalScene;
    private Scene scene;


    @Override
    public void start(Stage stage) throws Exception {
        this.principalScene = stage;
        principalScene.setTitle("Netflix | App recommender");
        homeScene();
        principalScene.show();
    }

    public void homeScene() {
        try {
            HomeController homeController = (HomeController) changeScene("HomeView.fxml", 400, 600);
            homeController.setPrincipalScene(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Initializable changeScene(String fxml, int width, int height) throws IOException {
        Initializable result = null;
        FXMLLoader uploadFXML = new FXMLLoader();
        InputStream file = Principal.class.getResourceAsStream(VIEWS_PACKAGES + fxml);
        uploadFXML.setBuilderFactory(new JavaFXBuilderFactory());
        uploadFXML.setLocation(Principal.class.getResource(VIEWS_PACKAGES + fxml));
        scene = new Scene((AnchorPane) uploadFXML.load(file), width, height);
        principalScene.setScene(scene);
        principalScene.sizeToScene();
        result = (Initializable) uploadFXML.getController();
        return result;
    }

    public static void main (String[] args){
        launch(args);
    }
}
