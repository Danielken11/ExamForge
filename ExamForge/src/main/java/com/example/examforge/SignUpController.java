package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpController{

@FXML
    PasswordField passwordNew;
@FXML
    GridPane gridPane;
@FXML
    CheckBox showPassword;
@FXML
    Button signButton;
@FXML
    TextField emailNew;
@FXML
    TextField loginNew;
@FXML
    Label msgLabel;
@FXML
    Label loginLabel;
@FXML
    BorderPane infoPane;
@FXML
    ImageView questionImage;
@FXML
    ImageView reqUndefined;

BorderPane mainPane;
BorderPane secondPane;
TextField passwordShowLabel;
Thread passwordFieldThread;
private Timeline validationTimeline;
private boolean passwdMatches = false;

public Server server;

private final int passwdSize = 8;
private final int maxNumbers = 1;
private int digitCounter;

public void setPanes(BorderPane mainPane,BorderPane secondPane){
    this.mainPane = mainPane;
    this.secondPane = secondPane;
}
public void changeImage(ImageView name,String url){
    Image newImage = new Image(url);
    name.setImage(newImage);
}
    public void setServer(Server server){
        this.server = server;
    }

    private void buttonInteraction(Button button){
        button.setOnMouseEntered(event -> {
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            button.setCursor(Cursor.DEFAULT);
        });
    }
    private void labelInteraction(Label label){
        label.setOnMouseEntered(event -> {
            label.setCursor(Cursor.HAND);
        });

        label.setOnMouseExited(event -> {
            label.setCursor(Cursor.DEFAULT);
        });
    }

private void scheduleLabelDisappearance(Label label) {

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
      label.setText("");
    }));
    timeline.play();
}
private void setupValidationTimeline() {
    validationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> createUserValidation()));
    validationTimeline.setCycleCount(Timeline.INDEFINITE);
    validationTimeline.play();
}

private void createUserValidation(){
    String passwd = passwordNew.getText();

    for(int i = 0; i<passwd.length(); i++){
        char c = passwd.charAt(i);
        if(Character.isDigit(c)){
            digitCounter++;
        }
    }

    if(passwd.length() >= passwdSize && digitCounter >=1){
        passwordNew.setStyle("-fx-border-color: transparent");
        passwordNew.setStyle("-fx-background-color: #706f6f");
        passwdMatches = true;
        changeImage(reqUndefined,String.valueOf(getClass().getResource("/com/example/examforge/assets/login/completedReq.png")));
    }else{
        passwordNew.setStyle("-fx-border-color: #ff3a3a");
        passwordNew.setStyle("-fx-background-color: #bb6464");
        passwdMatches = false;
        changeImage(reqUndefined,String.valueOf(getClass().getResource("/com/example/examforge/assets/login/uncompleted.png")));
    }
}

    private void setupListeners() {
        passwordNew.setOnMouseEntered(event -> validationTimeline.pause());
        passwordNew.setOnMouseExited(event -> validationTimeline.play());
    }

public void initialize(){

    buttonInteraction(signButton);
    labelInteraction(loginLabel);
    infoPane.setVisible(false);
    setupValidationTimeline();
    setupListeners();

    passwordShowLabel = new TextField();
    passwordShowLabel.setPromptText("Password");
    passwordShowLabel.setStyle("-fx-background-color: #706f6f;\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-pref-width: 280;\n" +
            "    -fx-pref-height: 35;\n" +
            "    -fx-background-radius: 9;\n" +
            "    -fx-font-weight: bold;");

    gridPane.setConstraints(passwordShowLabel,0,2);
    gridPane.getChildren().addAll(passwordShowLabel);
    passwordShowLabel.setVisible(false);

    showPassword.setOnAction(actionEvent -> {
        if(showPassword.isSelected()){
            passwordNew.setVisible(false);
            passwordShowLabel.setText(passwordNew.getText());
            passwordShowLabel.setVisible(true);
        }else{
            passwordNew.setText(passwordShowLabel.getText());
            passwordNew.setVisible(true);
            passwordShowLabel.setVisible(false);
        }
    });

    BooleanBinding emptyFields = emailNew.textProperty().isEmpty()
            .or(loginNew.textProperty().isEmpty())
            .or(passwordNew.textProperty().isEmpty());

    signButton.disableProperty().bind(emptyFields);

}

@FXML
private void signUP(ActionEvent event) throws IOException {

    if(passwdMatches == true){
        server.sendQuery("INSERT INTO Users (Email,Login,Password) VALUES('" + emailNew.getText() + "','" + loginNew.getText() + "','" + passwordNew.getText() + "');");
        mainPane.setLeft(secondPane);
    }else{
        msgLabel.setText("Inccorect inserted data");
        scheduleLabelDisappearance(msgLabel);
    }
}
@FXML
private void showReq(){
    infoPane.setVisible(true);
    questionImage.setOnMouseExited(mouseEvent -> {
        infoPane.setVisible(false);
    });
}
@FXML
public void loginScene(MouseEvent mouseEvent) {
    mainPane.setLeft(secondPane);
    }
}
