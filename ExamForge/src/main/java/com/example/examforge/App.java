package com.example.examforge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 600);
        scene.setOnMousePressed(this::onMousePressed);
        scene.setOnMouseDragged(this::onMouseDragged);

        Image icon = new Image(getClass().getResource("/com/example/examforge/assets/root/ExamForge.png").toExternalForm());

        stage.getIcons().add(icon);
        stage.setTitle("ExamForge");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        LoadingController loadingController = loader.getController();
        loadingController.setStage(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}