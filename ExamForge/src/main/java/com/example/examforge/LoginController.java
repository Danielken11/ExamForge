package com.example.examforge;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {

@FXML
    Button loginButton;
@FXML
    Label signUPlabel;
@FXML
    BorderPane mainView;
@FXML
    TextField login;
@FXML
    PasswordField password;
@FXML
    Button closeButton;
@FXML
    CheckBox showPassword;
@FXML
    GridPane gridPane;
@FXML
    BorderPane loginPane;

    TextField passwordShowLabel;

Parent root;
private Stage stage;

public void setStage(Stage stage) {
    this.stage = stage;
}

public void initialize() throws IOException {

    buttonInteraction(loginButton);
    buttonInteraction(closeButton);
    labelInteraction(signUPlabel);

    closeButton.setOnAction(actionEvent -> {
        stage.close();
    });

    passwordShowLabel = new TextField();
    passwordShowLabel.setPromptText("Password");
    passwordShowLabel.setStyle("-fx-background-color: #706f6f;\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-pref-width: 280;\n" +
            "    -fx-pref-height: 35;\n" +
            "    -fx-background-radius: 9;\n" +
            "    -fx-font-weight: bold;");

    gridPane.setConstraints(passwordShowLabel,0,1);
    gridPane.getChildren().addAll(passwordShowLabel);
    passwordShowLabel.setVisible(false);
  showPassword.setOnAction(actionEvent -> {
      if(showPassword.isSelected()){
          password.setVisible(false);
          passwordShowLabel.setText(password.getText());
          passwordShowLabel.setVisible(true);
      }else{
          password.setText(passwordShowLabel.getText());
          password.setVisible(true);
          passwordShowLabel.setVisible(false);
      }
  });




    BooleanBinding emptyFields = login.textProperty().isEmpty()
            .or(password.textProperty().isEmpty());

    loginButton.disableProperty().bind(emptyFields);
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
    private void login() throws IOException {





    Stage rootStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("root-view.fxml"));
    root = loader.load();

    Scene scene = new Scene(root,1000,600);
    rootStage.setScene(scene);
    rootStage.initStyle(StageStyle.UNDECORATED);

    rootStage.show();
    stage.close();

    RootController rootController = loader.getController();

    rootController.setStage(rootStage);
}
@FXML
    private void signUPScene() throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-view.fxml"));
       root = loader.load();
       mainView.setLeft(root);

       SignUpController signUpController = loader.getController();
       signUpController.setPanes(mainView,loginPane);
    }

}
