package com.example.quizme.quizMe;

import java.util.List;

public class Question {
    int id;
    String question, category, correctAnswer, difficulty;
    List<String> wrongAnswers;

    public Question(String question, String category, String correctAnswer, String difficulty){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() { return question; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getCorrectAnswer() { return correctAnswer; }

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getDifficulty() { return difficulty; }

    public void setWrongAnswers(List<String> wrongAnswers) { this.wrongAnswers = wrongAnswers; }

    public List<String> setWrongAnswers() { return wrongAnswers; }
}
