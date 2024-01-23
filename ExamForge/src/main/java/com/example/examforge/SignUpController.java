package com.example.examforge;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.regex.Pattern;

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

private static final Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).{6,10}$");
BorderPane mainPane;
BorderPane secondPane;
TextField passwordShowLabel;

public void setPanes(BorderPane mainPane,BorderPane secondPane){
    this.mainPane = mainPane;
    this.secondPane = secondPane;
}

private static boolean validate(String passwd){
    return pattern.matcher(passwd).matches();
}

public void initialize(){


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

    BooleanBinding emptyFields = emailNew.textProperty().isEmpty()
            .or(loginNew.textProperty().isEmpty())
            .or(passwordNew.textProperty().isEmpty());

    signButton.disableProperty().bind(emptyFields);
}

@FXML
private void signUP(){

    User newUser = new User(emailNew.getText().toString(),loginNew.getText().toString(),passwordNew.getText().toString());

    mainPane.setLeft(secondPane);

}

@FXML
public void loginScene(MouseEvent mouseEvent) {

    mainPane.setLeft(secondPane);


    }
}
