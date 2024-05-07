package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Timer;

public class RootController {

    @FXML
    Button minimizeButton, closeButton;
    @FXML
    HBox topBar;
    @FXML
    Button dashButton;
    @FXML
    BorderPane rootBorder;
    @FXML
    BorderPane mainView;
    @FXML
    Button settingsButton;
    @FXML
    GridPane statsGrid;
    @FXML
    CustomProgressIndicator prg1, prg2;
    @FXML
    AreaChart<String, Number> dataBaseChart;
    @FXML
    CategoryAxis n1;
    @FXML
    NumberAxis n2;
    @FXML
    ToggleButton changeButton, changeButton2;
    @FXML
    Label statusLabel;
    @FXML
    Button logOutButton;
    @FXML
        HBox exitPaneBox,changeStatsBox;
    @FXML
        Label d1,d2,d3;

    private Stage stage;
    private double xOffset;
    private double yOffset;
    private Parent root;
    Server server;
    public Thread connectionThread;
    public boolean connectionState;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setServer(Server server) {
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
    private void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Scene) event.getSource()).getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    public void setUserData(User user){
        this.user = user;
    }
    private void setData(){
        Platform.runLater(()->{
            d1.setText(user.email);
            d2.setText(user.login);
            d3.setText(user.status);
        });

    }
    public void initialize() {
        setData();
        
        buttonInteraction(logOutButton);
        exitPaneBox.setVisible(false);
        changeStatsBox.setVisible(false);

        Ellipse ellipse = new Ellipse();

        changeButton.setShape(ellipse);
        changeButton2.setShape(ellipse);

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

        closeButton.setOnAction(event -> {
            stage.close();
        });

        prg1.setColor(Color.web("#8f3e8f"));
        prg2.setColor(Color.WHITE);
        prg1.setProgress(0.7);
        prg2.setProgress(0.9);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jan", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Feb", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Mar", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Apr", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("May", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Jun", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Jul", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Aug", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Sep", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Oct", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Nov", Math.random() * (50 - 10) + 10));
        series.getData().add(new XYChart.Data<>("Dec", Math.random() * (50 - 10) + 10));

        n1.setTickLabelFill(Color.GRAY);
        n2.setTickLabelFill(Color.GRAY);

        n1.setTickMarkVisible(false);
        n2.setTickMarkVisible(false);

        dataBaseChart.getData().add(series);
//
//        Thread showStatus = new Thread(()->{
//           while(true){
//               try {
//                   Platform.runLater(()->{
//
//                      if(server.connected){
//                          statusLabel.setText("connected");
//                          statusLabel.setStyle("    -fx-font-size: 9;" +
//                                  "    -fx-text-fill: #15ec18;" +
//                                  "    -fx-font-weight: bold;" +
//                                  "    -fx-font-family: Arial;");
//
//                          System.out.println("connected");
//                      }else if(!server.connected) {
//                          statusLabel.setText("disconnected");
//                          statusLabel.setStyle("    -fx-font-size: 9;" +
//                                  "    -fx-text-fill: #ff3a3a;" +
//                                  "    -fx-font-weight: bold;" +
//                                  "    -fx-font-family: Arial;");
//                          System.out.println("disconnected");
//                      }
//                   });
//                   Thread.sleep(3000);
//               }catch (Exception ex){
//                   ex.printStackTrace();
//               }
//           }
//        });
//
//        showStatus.start();
}

@FXML
    private void exitPane(){
    if(changeButton.isSelected()){
        exitPaneBox.setVisible(true);
    }else{
        exitPaneBox.setVisible(false);
    }
}
@FXML
    private void signOut() throws IOException {
    stage.close();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
    root = loader.load();
    Scene scene = new Scene(root, 1000, 600);
    Stage loginStage = new Stage();

    scene.setOnMousePressed(this::onMousePressed);
    scene.setOnMouseDragged(this::onMouseDragged);

    loginStage.setScene(scene);
    loginStage.initStyle(StageStyle.UNDECORATED);
    loginStage.setTitle("ExamForge");
    loginStage.show();
    stage.close();

    LoginController loginController = loader.getController();
    loginController.setStage(loginStage);
    loginController.setServer(server);
}

@FXML
    private void dashScene(){
    rootBorder.setCenter(mainView);


}
    private void slideTransition(Parent root) {

        javafx.animation.TranslateTransition transition = new javafx.animation.TranslateTransition(Duration.seconds(0.5), root);

        root.setTranslateY(mainView.getWidth());

        transition.setToY(0);

        transition.play();
    }

@FXML
    private void dataScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);
        DataController dataController = loader.getController();
        dataController.setServer(server);
}
@FXML
    private void generateScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("generator-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);
        GeneratorController generatorController  = loader.getController();
        generatorController.setServer(server);
        generatorController.setPane(rootBorder);
}
@FXML
    private void settingsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        root = loader.load();
        slideTransition(root);
        rootBorder.setCenter(root);


    }
}
