package com.example.examforge;

public class Tickets {
    private String[] questions;

    public Tickets(){

    }

    public void setQuestions(String questions){
        this.questions = new String[]{questions};
    }
    public String[] getQuestions(){
        return this.questions;
    }

    @Override
    public String toString() {
        return questions + "\n";
    }
}
