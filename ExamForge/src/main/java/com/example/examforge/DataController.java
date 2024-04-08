package com.example.examforge;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class DataController {

@FXML
    Label l2;
@FXML
    ComboBox<String> comboTable;
@FXML
    TableView<Questions> dataTable;

public Server server;

public StringBuilder tables;

public void setServer(Server server) {
    this.server = server;
}

public void initialize(){

    getDBName();setDBTables();tableShowCase();

    tables = new StringBuilder();

    comboTable.setOnAction(event ->{
        String option =  comboTable.getValue();

        String query = "SELECT * FROM " + option;
        Questions[] questions = new Questions[20];

        try {
            server.sendQuery(query);
            StringBuilder tableData = new StringBuilder();
            tableData.append(server.in.readObject());

            String fullString = tableData.toString();
            String[] stringArray = fullString.split(",");

            for(int i = 0; i < stringArray.length && i < 20; i++){
                questions[i] = new Questions(i + 1, stringArray[i]);
            }

            ObservableList<Questions> list = FXCollections.observableArrayList(questions);

            dataTable.setItems(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    });
}
//Function for getting database name...
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
//Showing all db tables...
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
    public void tableShowCase(){

        Platform.runLater(()->{

            TableColumn<Questions,Integer> idColl = new TableColumn<>("Id");
            TableColumn<Questions,String> questionColl = new TableColumn<>("Questions");
            idColl.setCellValueFactory(new PropertyValueFactory<>("id"));
            questionColl.setCellValueFactory(new PropertyValueFactory<>("question"));

            idColl.setStyle("-fx-background-color:rgba(37, 33, 33, 0.49);");

            idColl.setPrefWidth(50);
            questionColl.setPrefWidth(800);

            dataTable.getColumns().addAll(idColl,questionColl);
//            dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        });
    }
}
