package com.example.examforge;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

public class LoadingController {
    @FXML
    ProgressIndicator serverLoadIndicator;

   private static Boolean isRunning = false;


public void initialize(){




}
//    private void scheduleLabelDisappearance(Label label) {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
//            label.setVisible(false);
//            label.setManaged(false);
//        }));
//        timeline.play();
//    }

//    private void cursorInteraction(Button button){
//        button.setOnMouseEntered(event -> {
//            button.setCursor(Cursor.HAND);
//        });
//
//        button.setOnMouseExited(event -> {
//            button.setCursor(Cursor.DEFAULT);
//        });
//    }
//    private void toggleCursorInteraction(ToggleButton button){
//        button.setOnMouseEntered(event -> {
//            button.setCursor(Cursor.HAND);
//        });
//
//        button.setOnMouseExited(event -> {
//            button.setCursor(Cursor.DEFAULT);
//        });
//    }
//public void initialize(){
//
//    try(
//            Socket socket = new Socket("192.168.100.237",19997);
//            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
//    ){
//        System.out.println("Connected to the Server...");
//        String query = "SELECT * FROM [Calculatoare].[dbo].[calculatoare.imprimante]";
//        out.writeObject(query);
//
//        List<Object> list = new ArrayList<>();
//
//        list.add(in.readObject().toString());
//
//
//        System.out.println(list);
//
//
//    }catch (IOException | ClassNotFoundException e){
//        System.out.println("Connection Interuptted!!!");
//        e.printStackTrace();
//    }


//    }
}
