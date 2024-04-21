package com.example.examforge;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomSpinner extends HBox {

private Button incrementButton,decrementButton;
public Label textField;
private HBox spinner;
public AtomicInteger var;

    private void buttonInteraction(Button button){
        button.setOnMouseEntered(event -> {
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            button.setCursor(Cursor.DEFAULT);
        });
    }

   public CustomSpinner(){
        spinner = createSpinner();
        initialize();
   }

   public void initialize(){
       getChildren().addAll(spinner);

//       this.textField.setText(String.valueOf(0));

       var = new AtomicInteger();
       this.incrementButton.setOnAction(event -> {
           var.getAndIncrement();
           this.textField.setText(String.valueOf(var));
       });

       this.decrementButton.setOnAction(event -> {
           int value = var.get();
           if (value > 1) {
               var.getAndDecrement();
               this.textField.setText(String.valueOf(var));

           }
       });
   }
   public int getValue(){
        int value = Integer.parseInt(textField.getText());
        return value;
   }
   public HBox createSpinner(){
       Circle circle = new Circle();

       this.incrementButton = new Button();
       this.decrementButton = new Button();

       this.textField = new Label();

       this.textField.setStyle("-fx-max-width: 25;-fx-pref-width: 25;-fx-background-color: transparent;");
       this.textField.setStyle("-fx-text-fill: white;-fx-font-size: 12;-fx-font-family: Verdana,sans-serif;-fx-font-weight: bold");

       this.incrementButton.setShape(circle); buttonInteraction(this.incrementButton);
       this.decrementButton.setShape(circle); buttonInteraction(this.decrementButton);

       this.incrementButton.setStyle("-fx-background-color: transparent");
       this.decrementButton.setStyle("-fx-background-color: transparent");

       Image IncImage = new Image(String.valueOf(getClass().getResource("/com/example/examforge/assets/generator/plusIco.png")));
       Image DecImage = new Image(String.valueOf(getClass().getResource("/com/example/examforge/assets/generator/minusIco.png")));

       ImageView imageView1 = new ImageView();imageView1.setImage(IncImage);
       ImageView imageView2 = new ImageView();imageView2.setImage(DecImage);

       this.incrementButton.setGraphic(imageView1);
       this.decrementButton.setGraphic(imageView2);

       HBox box = new HBox();
       box.setAlignment(Pos.CENTER);
       box.setStyle("-fx-background-color: transparent;-fx-pref-height: 40;-fx-max-height: 40");

       box.getChildren().addAll(incrementButton,textField,decrementButton);

       return box;
   }
}
