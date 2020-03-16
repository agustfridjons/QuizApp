package com.example.quizme.quizMe;

import java.util.List;

public class GameResults {
    int id, score;
    String category;
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

    // TODO getta og setta correctlyAnswered
}
