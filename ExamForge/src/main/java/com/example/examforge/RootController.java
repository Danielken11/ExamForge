package com.example.examforge;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
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

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class RootController {

    @FXML
    Button minimizeButton, closeButton;
    @FXML
    HBox topBar;
    @FXML
    Button dashButton,dataButton,generateButton,settingsButton;
    @FXML
    BorderPane rootBorder;
    @FXML
    BorderPane mainView;
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

    private double xOffset;private double yOffset;

    private Parent root;
    Server server;
    Settings settings;
    ObjectMapper objectMapper;

    Thread connectionThread;

    private User user;

    int currentPage = 1;

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

    private void buttonsOpacity(Button button){

        button.setOnMouseEntered(event -> {
            button.setOpacity(0.4);
        });

        button.setOnMouseExited(event -> {
            button.setOpacity(1.0);
        });
    }

    private void applySettings(){
        Platform.runLater(()->{
            try {
                File file = new File("appSettings.json");
                if (file.exists()) {
                    settings = new Settings();
                    objectMapper = new ObjectMapper();
                    settings = objectMapper.readValue(file, Settings.class);
                    System.out.println(settings.getLanguage());
                } else {
                    System.out.println("Error while reading settings");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void checkButtons(){
        dashButton.setStyle(currentPage == 1 ? "-fx-background-color:#494954" : "-fx-background-color:transparent;");
        dataButton.setStyle(currentPage == 2 ? "-fx-background-color:#494954" : "-fx-background-color:transparent;");
        generateButton.setStyle(currentPage == 3 ? "-fx-background-color:#494954" : "-fx-background-color:transparent;");
        settingsButton.setStyle(currentPage == 4 ? "-fx-background-color:#494954" : "-fx-background-color:transparent;");
    }

//    private void setData(){
//        Platform.runLater(()->{
//            d1.setText(user.email);
//            d2.setText(user.login);
//            d3.setText(user.status);
//        });
//    }

    public void serverStatus() throws IOException {

        StringBuilder received = new StringBuilder();

         connectionThread = new Thread(()->{
            while(true){
                try {
                    Platform.runLater(()->{
                        try {
                            received.setLength(0);
                            server.sendQuery("connectionStatus");
                            received.append(server.in.readObject());

                            if(!received.isEmpty()){
                                statusLabel.setText("connected");
                                statusLabel.setStyle("    -fx-font-size: 9;" +
                                        "    -fx-text-fill: #15ec18;" +
                                        "    -fx-font-weight: bold;" +
                                        "    -fx-font-family: Arial;");

                                System.out.println("connected");
                            }
                        } catch (IOException e) {
                            statusLabel.setText("disconnected");
                            statusLabel.setStyle("    -fx-font-size: 9;" +
                                    "    -fx-text-fill: #ff3a3a;" +
                                    "    -fx-font-weight: bold;" +
                                    "    -fx-font-family: Arial;");
                            System.out.println("disconnected");
                            server.connect();
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    });
                    Thread.sleep(5000);
                }catch (Exception ex){
                    Thread.currentThread().interrupt();
                  ex.printStackTrace();
                }
            }
        });

        connectionThread.setDaemon(true);
        connectionThread.start();

    }
    public void initialize() throws IOException {

//        setData();
        serverStatus();checkButtons();buttonInteraction(logOutButton);
        buttonsOpacity(dashButton);buttonsOpacity(dataButton);
        buttonsOpacity(generateButton);buttonsOpacity(settingsButton);
        applySettings();

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
    private void dashScene() throws IOException {
    rootBorder.setCenter(mainView);
    currentPage = 1;
    checkButtons();
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
        currentPage = 2;
        checkButtons();
}
@FXML
    private void generateScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("generator-view.fxml"));
        root = loader.load();
        rootBorder.setCenter(root);
        GeneratorController generatorController  = loader.getController();
        generatorController.setServer(server);
        generatorController.setPane(rootBorder);
        currentPage = 3;
        checkButtons();
}
@FXML
    private void settingsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        root = loader.load();
        slideTransition(root);
        rootBorder.setCenter(root);
        currentPage = 4;
        checkButtons();
    }
}
