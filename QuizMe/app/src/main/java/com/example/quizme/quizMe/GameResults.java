package com.example.quizme.quizMe;

public class GameResults {
    int id, score, userAnswer;
    String category, correctAnswer, gameid, difficulty;

    public GameResults(String category, String difficulty, Integer userAnswer, String correctAnswer, String gameid) {
        this.category = category;
        this.difficulty = difficulty;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.gameid = gameid;
    }
    // TODO List<Question, Boolean> correctlyAnswered;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() { return score; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getGameid() {
        return gameid;
    }

    public Integer getUserAnswers() { return userAnswer; }

    public void setUserAnswers(Integer userAnswer) { this.userAnswer = userAnswer; }

    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getCorrectAnswer() { return correctAnswer; }

    // TODO getta og setta correctlyAnswered
}
