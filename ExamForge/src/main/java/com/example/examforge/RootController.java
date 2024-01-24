package com.example.examforge;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class RootController {

@FXML
    Button minimizeButton;
@FXML
    Button maximizeButton;
@FXML
    Button closeButton;
@FXML
    HBox topBar;
@FXML
    Button dashButton;
@FXML
    BorderPane rootBorder;
@FXML
    BorderPane mainView;

private Stage stage;
private double xOffset;
private double yOffset;
private boolean isMaximized = false;
private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){

        topBar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        minimizeButton.setOnAction(event -> {
            stage.setIconified(true);
        });

        maximizeButton.setOnAction(event -> {
            if (isMaximized) {
                stage.setX(xOffset);
                stage.setY(yOffset);
                stage.setWidth(1150);
                stage.setHeight(700);
                stage.setOpacity(0.99);
            } else {
                Screen screen = Screen.getPrimary();
                stage.setX(screen.getVisualBounds().getMinX());
                stage.setY(screen.getVisualBounds().getMinY());
                stage.setWidth(screen.getVisualBounds().getWidth());
                stage.setHeight(screen.getVisualBounds().getHeight());
                xOffset = stage.getX();
                yOffset = stage.getY();
                stage.setOpacity(1.0);
            }
            isMaximized = !isMaximized;
        });

        closeButton.setOnAction(event -> {
            stage.close();
        });

}

@FXML
    private void dashScene(){
    rootBorder.setCenter(mainView);

}

@FXML
    private void dataScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);


}
@FXML
    private void generateScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("generator-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);
}
@FXML
    private void settingsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);
}


}
