package com.example.examforge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SettingsController {

@FXML
    ComboBox<String> combo1;

    public void initialize(){
        ObservableList<String> list1 = FXCollections.observableArrayList("English","Romana","Руский");

        combo1.setItems(list1);
        combo1.getSelectionModel().selectFirst();

    }

}
