package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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
@FXML
    Label msgLabel;

    TextField passwordShowLabel;

Parent root;
private Stage stage;
private Server server;

public void setStage(Stage stage) {
    this.stage = stage;
}

public void setServer(Server server){
    this.server = server;
}
    private void scheduleLabelDisappearance(Label label) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            label.setText("");
        }));
        timeline.play();
    }

public void initialize(){

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

    loginPane.setOnKeyPressed(event->{
        if(event.getCode() == KeyCode.ENTER && !loginButton.isDisable()){
            try {
                login();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    });

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
    private void login() throws IOException, ClassNotFoundException {

//    String query = "SELECT * FROM [Calculatoare].[dbo].[Users] WHERE Login = '" +  login.getText() + "' AND Password = '" + password.getText() + "'";
//
//    server.sendQuery(query);
//    String receivedData = (String) server.in.readObject();
//
//    System.out.println(receivedData);
//
//    if(receivedData.isEmpty()){
//        System.out.println("Empty data");
//        msgLabel.setText("Invalid login credentials. Please check your username and password.");
//        scheduleLabelDisappearance(msgLabel);
//    }else{
        Stage rootStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("root-view.fxml"));
        root = loader.load();

//Creating a clip with triangle for making stage with rounded corners ,because javafx don't have the implementation of custom windows decoration
        root.setStyle("-fx-background-radius: 8;-fx-border-radius: 8");
        Rectangle rect = new Rectangle(1150,700);
        rect.setArcHeight(15.0);
        rect.setArcWidth(15.0);
        root.setClip(rect);

        Scene scene = new Scene(root,1150,700);
        scene.setFill(Color.TRANSPARENT);
        rootStage.setScene(scene);
        rootStage.initStyle(StageStyle.TRANSPARENT);
        rootStage.setOpacity(0.99);
        rootStage.setTitle("ExamForge");

        rootStage.show();
        stage.close();

        RootController rootController = loader.getController();

//        String[] arrayString = receivedData.split(",");

//        User currentUser = new User(arrayString[0],arrayString[1],arrayString[2],"Student");

//        rootController.setUserData(currentUser);
        rootController.setStage(rootStage);
        rootController.setServer(server);
//    }
}

@FXML
    private void signUPScene() throws IOException {

    login.clear();
    password.clear();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-view.fxml"));
       root = loader.load();
       mainView.setLeft(root);

       SignUpController signUpController = loader.getController();
       signUpController.setPanes(mainView,loginPane);
       signUpController.setServer(server);
    }

}
