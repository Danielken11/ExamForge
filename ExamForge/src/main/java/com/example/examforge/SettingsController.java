package com.example.examforge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;

public class SettingsController {

@FXML
    ComboBox<String> combo1;
@FXML
    Label labelSettings;

    Settings settings,apply;

    ObjectMapper objectMapper;

//I think we need to change this radically...because is dumb way of applying settings...

    private void writeSettings() throws IOException {
        settings = new Settings();settings.setLanguage(combo1.getValue());

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("appSettings.json"),settings);

    }

    private void refresh() throws JsonProcessingException {
        initialize();
    }
    private void changeLanguage() throws JsonProcessingException {

        String data = apply.getLanguage();

        switch (data){
            case "English": labelSettings.setText("Language"); break;
            case "Romana": labelSettings.setText("Limba"); break;
            case "Руский":labelSettings.setText("Язык"); break;
        }
    }

    @FXML
    public void changeSettings() throws IOException {
        writeSettings();
        changeLanguage();
        refresh();
    }

    public void initialize() throws JsonProcessingException {

        objectMapper = new ObjectMapper();
        ObservableList<String> list1 = FXCollections.observableArrayList("English","Romana","Руский");
        try {
            File file = new File("appSettings.json");
            if (file.exists()) {
                apply = new Settings();
                apply = objectMapper.readValue(file, Settings.class);
                combo1.getSelectionModel().select(apply.getLanguage());
                changeLanguage();
            } else {
                System.out.println("Error while reading settings");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        combo1.setItems(list1);

    }
}
