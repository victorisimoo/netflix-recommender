package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UploadFileController implements Initializable {
    private Principal principalScene;
    private List<String> files;

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

    public void uploadFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", files));
        File route = fc.showOpenDialog(null);
        if (route != null) {
            txtNameFile.setText(route.getAbsolutePath());
        }
    }

    public void profileScene(){
        principalScene.profileScene();
    }
}
