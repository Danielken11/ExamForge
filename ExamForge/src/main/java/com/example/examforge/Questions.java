package com.example.examforge;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Questions {

    public SimpleIntegerProperty id;
    public SimpleStringProperty question;

    public Questions(int id,String question){
        this.id = new SimpleIntegerProperty(id);
        this.question =  new SimpleStringProperty(question);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProprety(){
        return id;
    }

    public String getQuestion() {
        return question.get();
    }

    public SimpleStringProperty stringProperty() {
        return question;
    }
}
