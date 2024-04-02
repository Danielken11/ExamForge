package com.example.examforge;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class DataController {

@FXML
    Label l2;
    Server server;

public void setServer(Server server) {
    this.server = server;
}

public void initialize() throws IOException, ClassNotFoundException {

    getDBName();

}

public void getDBName(){
    Platform.runLater(()->{
        try {
            String query = "SELECT DB_NAME();";
            server.sendQuery(query);
            String dbName = (String) server.in.readObject();
            l2.setText(l2.getText() +  dbName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    });
}

//@FXML
//    TableView<Questions> dataTable;
//
//    public void initialize(){
//
//        TableColumn<Questions,Integer> idColl = new TableColumn<>("Id");
//        TableColumn<Questions,String> questionColl = new TableColumn<>("Questions");
//
//        dataTable.getColumns().addAll(idColl,questionColl);
//
//       Questions firstquestion = new Questions(1,"Prima Intrebare");
//
//       ArrayList<Questions> questions = new ArrayList<>();
//       questions.add(firstquestion);
//
//       ObservableList<Questions> list = FXCollections.observableArrayList(questions);
//
//       dataTable.setItems(list);

//    }

}
