package com.example.examforge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
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

            Image icon = new Image(getClass().getResource("/com/example/examforge/assets/root/ExamForge.png").toExternalForm());

            loginStage.getIcons().add(icon);
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

    Thread connectionThread = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            server = new Server();
            if (server.connected) {
                isRunning = true;
                try {
                    runLoginView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            } else {
                System.out.println("Error");
            }
        }
    });

    connectionThread.start();
 }
}
