package com.netflix.controller;

import com.netflix.app.Principal;
import com.netflix.app.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class ProfileController
 * @author victorisimo
 */
public class ProfileController implements Initializable {
    private ObservableList<String> searchType = FXCollections.observableArrayList("Título","Género", "Director");
    private Principal principalScene;
    @FXML private ComboBox cmbTypeSearch;
    @FXML private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTypeSearch.setItems(searchType); //Set the items of the combobox
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
}
