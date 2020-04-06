package com.example.quizme.quizMe;

public class Question {
    private int id;
    private int number;
    private String question, category, correctAnswer, difficulty;
    private String[] wrongAnswers;
    private int userAnswer;

    public Question(String question, String correctAnswer, String[] wrongAnswers) {
        this.question = question;
        this.wrongAnswers = wrongAnswers;
        this.correctAnswer = correctAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public void setGameNumber(int number) {
        this.number = number;
    }

    public int getGameNumber() { return number; }

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

    public void setWrongAnswers(String[] wrongAnswers) { this.wrongAnswers = wrongAnswers; }

    public String[] getWrongAnswers() { return wrongAnswers; }

    public Integer getUserAnswers() { return userAnswer; }

    public void setUserAnswers(Integer userAnswer) { this.userAnswer = userAnswer; }

}
