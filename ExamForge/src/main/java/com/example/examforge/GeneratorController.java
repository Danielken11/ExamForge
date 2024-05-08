package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

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
@FXML
    CheckBox checkLine;
@FXML
    BorderPane generatorBorder;

public Server server;
private StringBuilder tableData;
private  ObservableList<String> questionList;
private String pathURLData;
private BorderPane mainView;
Parent root;
String fileExtension;

private void buttonInteraction(Button button){
    button.setOnMouseEntered(event -> {
        button.setCursor(Cursor.HAND);
    });

    button.setOnMouseExited(event -> {
        button.setCursor(Cursor.DEFAULT);
    });
}
public void setPane(BorderPane borderPane){
    this.mainView = borderPane;
}

    private void sheduleInfoKill(Parent pane) {

        javafx.animation.TranslateTransition transition = new javafx.animation.TranslateTransition(Duration.seconds(0.5), pane);

        pane.setTranslateX(generatorBorder.getWidth());

        transition.setToX(0);

        transition.play();

    }

private void writeToFile(ArrayList<String> list, String fileUrl) throws IOException {

   if(fileExtension.matches("docx")) {

       XWPFDocument document = new XWPFDocument();
       try (FileOutputStream out = new FileOutputStream(fileUrl)) {
           int quantity = quantitySpinner.getValue();
           int numbers = numberSpinner.getValue();

           for (int i = 0; i < quantity; i++) {
               Collections.shuffle(list);
               List<String> ticketQuestions = list.subList(0, numbers);

               for (String question : ticketQuestions) {
                   XWPFParagraph paragraph = document.createParagraph();
                   XWPFRun run = paragraph.createRun();
                   run.setText(question);
               }
               if (checkLine.isSelected()) {
                   XWPFParagraph separator = document.createParagraph();
                   XWPFRun sepRun = separator.createRun();
                   sepRun.setText("---------------------------------------------------------------------------------------------------------------------");
               }
               document.createParagraph().createRun().addBreak();
           }
           document.write(out);
           System.out.println("DOCX file created successfully!");
       } catch (Exception ex) {
           throw new IOException("Error creating DOCX file: " + ex.getMessage());
       }
   }else if (fileExtension.matches("pdf")){

       Document document = new Document();
       try {
           PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
           document.open();

           int quantity = quantitySpinner.getValue();
           int numbers = numberSpinner.getValue();

           for (int i = 0; i < quantity; i++) {
               Collections.shuffle(list);
               List<String> ticketQuestions = list.subList(0, numbers);

               for (String question : ticketQuestions) {
                   document.add(new Paragraph(question));
               }
               if (checkLine.isSelected()) {
                   document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------"));
               }
               document.add(new Paragraph("\n"));
           }
           System.out.println("PDF file created successfully!");
       } catch (Exception ex) {
           throw new IOException("Error creating PDF file: " + ex.getMessage());
       } finally {
           document.close();
       }

   }else if(fileExtension.matches("txt") || fileExtension.matches("json") || fileExtension.matches("doc")){
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUrl, false))) {

           int quantity = quantitySpinner.getValue();
           int numbers = numberSpinner.getValue();

           for(int i = 0; i<quantity;i++){

               Collections.shuffle(list);

               List<String> ticketQuestions = list.subList(0, numbers);

               for (String question : ticketQuestions) {
                   writer.write(question);
                   writer.newLine();
               }
               if (checkLine.isSelected()) {
                   writer.newLine();
                   writer.write("---------------------------------------------------------------------------------------------------------------------\n");
                   writer.newLine();
               }else{
                   writer.newLine();
                   writer.write("");
                   writer.newLine();
               }
           }
       } catch (IOException ex) {
           throw new IOException("Error writing to file: " + ex.getMessage());
       }
   }else{
       System.out.println("Unknown file extension");
   }
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
            String[] result =  dbName.split(",");
            String s1 = result[0];
            String finalName = s1.toString();
            l1.setText(l1.getText() +  finalName);
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
    private void generate() throws IOException {

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
                Collections.shuffle(arrayList);
                writeToFile(arrayList,pathURLData);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("done-view.fxml"));
                root = loader.load();
                DoneController doneController = loader.getController();
                doneController.getFilePath(pathURLData);
                doneController.setPanes(mainView,generatorBorder);
                mainView.setCenter(root);
                sheduleInfoKill(root);

            }
        }
    }

    public void getPath() {

        FileChooser file = new FileChooser();
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File","*txt","*pdf","*docx","*doc","*json"));

        Stage openStage = new Stage();
        File openFile = file.showOpenDialog(openStage);
        pathUrl.setText(String.valueOf(openFile));
        pathURLData = String.valueOf(openFile);

        if(openFile == null) {
            pathUrl.setText("");
        }else{
            String fileName = openFile.getName();

            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, openFile.getName().length());
            System.out.println(fileExtension);
        }

    }
}
