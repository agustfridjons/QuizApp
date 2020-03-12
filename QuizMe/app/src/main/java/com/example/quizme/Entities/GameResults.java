package com.example.quizme.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity(name = "GameResults")
@Table(name = "game_results")
public class GameResults {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private String category;
    private Long date;

    @OneToMany(
            //mappedBy = "gameresults",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    public GameResults() {}

    public GameResults(Long id, int score, String category) {
        this.id = id;
        this.score = score;
        this.category = category;
        this.date = new Date().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setGameResults(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setGameResults(null);
    }
}
