package com.example.examforge;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoadingController {
    @FXML
    ProgressIndicator serverLoadIndicator;
    @FXML
    BorderPane firstContainer;

    private Parent root;
    private static Boolean isRunning = false;
    private Stage stage;
    private Server server;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private double xOffset;
    private double yOffset;

    private void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Scene) event.getSource()).getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

public void runLoginView() throws IOException{

    Platform.runLater(() -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            root = loader.load();
            Scene scene = new Scene(root, 1000, 600);
            Stage loginStage = new Stage();

            scene.setOnMousePressed(this::onMousePressed);
            scene.setOnMouseDragged(this::onMouseDragged);

            loginStage.setScene(scene);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setTitle("ExamForge");
            loginStage.show();
            stage.close();

            LoginController loginController = loader.getController();
            loginController.setStage(loginStage);
            loginController.setServer(server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
}

public void initialize() throws IOException, InterruptedException {

  Thread connectionThread = new Thread(() ->{
      while (true){
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          server = new Server();
          if(server.connected){
              isRunning = true;
              try {
                  runLoginView();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
              break;
          }else{
              System.out.println("Error");
          }
      }
  });

  connectionThread.start();



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
