package com.netflix.controller;

import com.netflix.app.Principal;
import com.netflix.app.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private Principal principalScene;

    @FXML
    private ComboBox cmbTypeSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Storage.getInstance().getName_actual_user());
        cmbTypeSearch.setItems(searchType);
    }

    public void setPrincipalScene(Principal principalScene){
        this.principalScene = principalScene;
    }

    public void searchScene(){
        this.principalScene.searchScene();
    }
}
