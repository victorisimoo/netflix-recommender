package com.netflix.controller;

import com.netflix.app.Principal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private Principal principalScene;

    @FXML
    private ComboBox cmbTypeSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTypeSearch.setItems(searchType);
    }

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    public void homeScene(){
        this.principalScene.profileScene();
    }
}