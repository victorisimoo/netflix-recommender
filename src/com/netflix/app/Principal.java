package com.netflix.app;

import com.netflix.controller.HomeController;
import com.netflix.controller.ProfileController;
import com.netflix.controller.RegisterController;
import com.netflix.controller.UploadFileController;
import javafx.application.Application;
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
 * scenario management class
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

    /***
     * Method for displaying the login screen.
     */
    public void homeScene() {
        try {
            HomeController homeController = (HomeController) changeScene("HomeView.fxml", 400, 600);
            homeController.setPrincipalScene(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Method for displaying the registration screen.
     */
    public void registerScene(){
        try{
            RegisterController registerController = (RegisterController) changeScene("RegisterView.fxml", 400, 600);
            registerController.setPrincipalScene(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method for displaying the upload file screen.
     */
    public void uploadFileScene(){
        try{
            UploadFileController uploadFileController = (UploadFileController) changeScene("UploadFileView.fxml", 400, 600);
            uploadFileController.setPrincipalScene(this);
        }catch  (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * Method for displaying the Profile screen.
     */
    public void profileScene(){
        try{
            ProfileController profileController = (ProfileController) changeScene("ProfileView.fxml", 400, 600);
            profileController.setPrincipalScene(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method for changing scenes
     * @param fxml fxml file to display
     * @param width dimensions
     * @param height dimensions
     * @return scene
     * @throws IOException error
     */
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

    /**
     * Main method to execute
     * @param args
     */
    public static void main (String[] args){
        launch(args);
    }
}
