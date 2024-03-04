package com.example.examforge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
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
    Button changeButton, changeButton2;
    @FXML
    Label statusLabel;

    private Stage stage;
    private double xOffset;
    private double yOffset;
    private Parent root;
    Server server;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void initialize() {

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

//    Thread connectionThread = new Thread(()->{
//        while(true){
//            try {
//                Platform.runLater(()->{
//                    if(server.socket.isConnected()){
//                        statusLabel.setText("connected");
//                        statusLabel.setStyle("    -fx-font-size: 9;" +
//                                "    -fx-text-fill: #15ec18;" +
//                                "    -fx-font-weight: bold;" +
//                                "    -fx-font-family: Arial;");
//                    }else{
//                        statusLabel.setText("disconnected");
//                        statusLabel.setStyle("    -fx-font-size: 9;" +
//                                "    -fx-text-fill: #ff3a3a;" +
//                                "    -fx-font-weight: bold;" +
//                                "    -fx-font-family: Arial;");
//                    }
//                });
//                Thread.sleep(100);
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//    });
//    connectionThread.setDaemon(true);
//    connectionThread.start();
        Task<Boolean> connectionTask = new Task<>() {
            @Override
            protected Boolean call() {
                return server.socket.isConnected();
            }
        };

        connectionTask.setOnSucceeded(event -> {
            boolean isConnected = connectionTask.getValue();
            if (isConnected) {
                statusLabel.setText("Connection successful!");
            } else {
                statusLabel.setText("Connection failed.");
            }
        });

        connectionTask.setOnFailed(event -> {
            statusLabel.setText("Connection check failed.");
        });

        Thread connectionThread = new Thread(connectionTask);
        connectionThread.start();
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
        System.out.println(server.socket.isConnected());
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
        slideTransition(root);
        rootBorder.setCenter(root);


    }
}
