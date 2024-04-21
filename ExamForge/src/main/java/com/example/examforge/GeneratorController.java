package com.example.examforge;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GeneratorController {

@FXML
    Label l1;
@FXML
    ComboBox<String> comboTable;
@FXML
    Button pathButton,forgeButton;
@FXML
    CustomSpinner quantitySpinner,numberSpinner;
@FXML
    TextField pathUrl;

public Server server;
private StringBuilder tableData;
private  ObservableList<String> questionList;
private String pathURLData;

private void buttonInteraction(Button button){
    button.setOnMouseEntered(event -> {
        button.setCursor(Cursor.HAND);
    });

    button.setOnMouseExited(event -> {
        button.setCursor(Cursor.DEFAULT);
    });
}

public void setServer(Server server) {
    this.server = server;
}

public void initialize(){
    getDBName();setDBTables();

    buttonInteraction(pathButton);
    buttonInteraction(forgeButton);

    forgeButton.setDisable(true);

    BooleanBinding requirments = pathUrl.textProperty().isEmpty()
            .or(numberSpinner.textField.textProperty().isEmpty())
            .or(quantitySpinner.textField.textProperty().isEmpty())
            .or(comboTable.getSelectionModel().selectedItemProperty().isNull());

    forgeButton.disableProperty().bind(requirments);

}

public void getDBName(){
    Platform.runLater(()->{
        try {
            String query = "SELECT DB_NAME();";
            server.sendQuery(query);
            String dbName = (String) server.in.readObject();
            l1.setText(l1.getText() +  dbName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    });
    }
    public void setDBTables(){
        Platform.runLater(()->{
            String getTablesQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
            try {
                server.sendQuery(getTablesQuery);
                StringBuilder receivedDatda = new StringBuilder();
                receivedDatda.append(server.in.readObject());
                ObservableList<String> list = FXCollections.observableArrayList();

                String fullString = receivedDatda.toString();
                String[] stringArray = fullString.split(",");

                list.addAll(stringArray);

                comboTable.setItems(list);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void generate(){

        if(!comboTable.getItems().isEmpty()) {
            String selectedTableName = comboTable.getSelectionModel().getSelectedItem();
            String query = "SELECT * FROM " + selectedTableName;

            try {
                server.sendQuery(query);

                tableData = new StringBuilder();
                tableData.append(server.in.readObject());
                questionList = FXCollections.observableArrayList();

                String fullString = tableData.toString();
                String[] stringArray = fullString.split(",");

                questionList.addAll(stringArray);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException();
            }

            if (!questionList.isEmpty()) {
                ArrayList<String> arrayList = new ArrayList<>(questionList);


            }
        }
    }

    public void getPath() {

        FileChooser file = new FileChooser();
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Txt File","*txt"));
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json File","*json"));
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Word File","*docx"));
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Old word File","*doc"));

        Stage openStage = new Stage();
        File openFile = file.showOpenDialog(openStage);
        pathUrl.setText(String.valueOf(openFile));

        if(openFile == null) {
            pathUrl.setText("");
        }

    }
}
