package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DoneController {

@FXML
    BorderPane doneBorderPane;
@FXML
    StackPane ticketInfoPane;
@FXML
    Label infoLabel10;
@FXML
    Button againButton;

String filePath;
Boolean writed = false;
Arc arc;
BorderPane mainView,generatorPane;

public void getFilePath(String filePath){
    this.filePath = filePath;
}
public void setPanes(BorderPane mainView,BorderPane generatorPane){
        this.mainView = mainView;
        this.generatorPane = generatorPane;
    }

private void buttonInteraction(Button button){
    button.setOnMouseEntered(event -> {
        button.setCursor(Cursor.HAND);
    });

    button.setOnMouseExited(event -> {
        button.setCursor(Cursor.DEFAULT);
    });
}

public void setInfoProgress(){

    arc.setVisible(false);
    arc.setManaged(false);
    Image image = new Image(String.valueOf(getClass().getResource("/com/example/examforge/assets/generator/icons8-done-100 (1).png")));
    ImageView imageView = new ImageView(image);

    ticketInfoPane.getChildren().add(imageView);

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),event -> {
        againButton.setVisible(true);
        imageView.setVisible(false);
    }));

    timeline.play();

    infoLabel10.setText("Generated Success!");

}

public void dataProgressAnimation(){

    arc = new Arc();
    arc.setRadiusX(40);
    arc.setRadiusY(40);
    arc.setStartAngle(90);
    arc.setType(ArcType.OPEN);
    arc.setStrokeWidth(5);
    arc.setFill(null);
    arc.setStroke(Color.WHITE);

    ticketInfoPane.getChildren().add(arc);

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(arc.lengthProperty(), 0)),
            new KeyFrame(Duration.seconds(2), new KeyValue(arc.lengthProperty(), 360))
    );

    timeline.setCycleCount(1);
    timeline.setOnFinished(event -> setInfoProgress());

    timeline.play();

}

public void checkData(){
    Platform.runLater(()->{

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line;

                while((line = reader.readLine()) != null){
                    System.out.println(line);
                    writed = true;
                }
                if(!writed){
                   System.out.println("Error while reading the file...");
                }else{
                    dataProgressAnimation();
                }
        }catch (IOException ex){
            throw new RuntimeException();
        }
    });
}
@FXML
    public void again(){
    mainView.setCenter(generatorPane);
}

public void initialize(){

  checkData();
  againButton.setVisible(false);
  buttonInteraction(againButton);

    }
}
