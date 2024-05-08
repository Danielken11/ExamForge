package com.example.examforge;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class CustomProgressIndicator extends StackPane {

    private final DoubleProperty progress = new SimpleDoubleProperty(0.0);
    public Arc completedArc;

    public CustomProgressIndicator() {
        completedArc = createArc();
        initialize();
    }

    public void setColor(Color color){
        completedArc.setStroke(color);
    }

    private void initialize() {
        getChildren().addAll(completedArc);
    }

    private Arc createArc() {
        Arc arc = new Arc();
        arc.setRadiusX(25);
        arc.setRadiusY(25);
        arc.setStartAngle(-90);
        arc.setLength(360);
        arc.setType(ArcType.OPEN);  
        arc.setStrokeWidth(5);
        arc.setFill(null);
        return arc;
    }

    public double getProgress() {
        return progress.get();
    }

    public void setProgress(double value) {
        progress.set(value);
        completedArc.setLength(value * 360);
    }

    public DoubleProperty progressProperty() {
        return progress;
    }
}
