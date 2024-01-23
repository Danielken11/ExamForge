package com.example.examforge;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RootController {

@FXML
    Button minimizeButton;
@FXML
    Button maximizeButton;
@FXML
    Button closeButton;
@FXML
    HBox topBar;

private Stage stage;
private double xOffset;
private double yOffset;
private boolean isMaximized = false;

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
                stage.setWidth(1000);
                stage.setHeight(600);
            } else {
                Screen screen = Screen.getPrimary();
                stage.setX(screen.getVisualBounds().getMinX());
                stage.setY(screen.getVisualBounds().getMinY());
                stage.setWidth(screen.getVisualBounds().getWidth());
                stage.setHeight(screen.getVisualBounds().getHeight());
                xOffset = stage.getX();
                yOffset = stage.getY();
            }
            isMaximized = !isMaximized;
        });

        closeButton.setOnAction(event -> {
            stage.close();
        });

}


}
