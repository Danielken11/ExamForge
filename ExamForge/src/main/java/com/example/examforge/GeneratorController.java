package com.example.examforge;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class GeneratorController {

@FXML
    Label l1;
@FXML
    ComboBox comboTable;

public Server server;

public void setServer(Server server) {
    this.server = server;
}

public void initialize(){
    getDBName();setDBTables();
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
}
