package com.example.examforge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SettingsController {

@FXML
    ComboBox combo1;
@FXML
    ComboBox combo2;

    public void initialize(){
        ObservableList<String> list1 = FXCollections.observableArrayList("English","Romana","Руский");
        ObservableList<String> list2 = FXCollections.observableArrayList("Default","Dark");

        combo1.setItems(list1);
        combo2.setItems(list2);
        combo1.getSelectionModel().selectFirst();
        combo2.getSelectionModel().selectFirst();
    }

}
