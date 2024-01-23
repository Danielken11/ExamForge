package com.example.examforge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class App extends Application {

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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 600);
        scene.setOnMousePressed(this::onMousePressed);
        scene.setOnMouseDragged(this::onMouseDragged);

        stage.setTitle("ExamForge");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        LoginController loginController = loader.getController();
        loginController.setStage(stage);


//For the connection to the server after the start of the loading screen we need to create a thread with the progress indicator...
//
//        try(
//                Socket socket = new Socket("192.168.100.237",19997);
//                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
//        ){
//            System.out.println("Connected to the Server...");
//            String query = "SELECT * FROM [Calculatoare].[dbo].[calculatoare.imprimante]";
//            out.writeObject(query);
//
//            List<Object> list = new ArrayList<>();
//
//            list.add(in.readObject().toString());
//
//
//            System.out.println(list);
//
//
//        }catch (IOException | ClassNotFoundException e){
//            System.out.println("Connection Interuptted!!!");
//            e.printStackTrace();
//        }

    }

    public static void main(String[] args) {
        launch();
    }
}