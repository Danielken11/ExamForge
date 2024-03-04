package com.example.examforge;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

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

public Server server;

public void setPanes(BorderPane mainPane,BorderPane secondPane){
    this.mainPane = mainPane;
    this.secondPane = secondPane;
}
public void changeImage(ImageView name){
    Image newImage = new Image("com/example/examforge/assets/login/completedReq.png");
    name.setImage(newImage);
}
    public void setServer(Server server){
        this.server = server;
    }


private static boolean validate(String passwd){
    if ((passwd.length() >=8) &&
            (passwd.matches("[a-z]")) &&
            (passwd.matches("[0-9]")))
        return true;
    else
        return false;
}

public void initialize(){

    buttonInteraction(signButton);
    labelInteraction(loginLabel);
    infoPane.setVisible(false);

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

    Boolean passwdRequirement;
    passwdRequirement = validate(passwordNew.getText());

    if(passwdRequirement == true){
        changeImage(reqUndefined);
        msgLabel.setText("meow");
    }

    BooleanBinding emptyFields = emailNew.textProperty().isEmpty()
            .or(loginNew.textProperty().isEmpty())
            .or(passwordNew.textProperty().isEmpty());

    signButton.disableProperty().bind(emptyFields);
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

@FXML
private void signUP() throws IOException {

   server.sendQuery("INSERT INTO Users (Email,Login,Password) VALUES('" + emailNew.getText() + "','" + loginNew.getText() + "','" + passwordNew.getText() + "');");
    mainPane.setLeft(secondPane);
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
