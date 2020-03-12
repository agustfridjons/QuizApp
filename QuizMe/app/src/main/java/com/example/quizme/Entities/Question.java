package com.example.quizme.Entities;

import javax.persistence.*;

@Entity(name = "Question")
@Table(name="question")
public class Question {

    // Key vesen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String question;
    private String correctAnswer;
    private Boolean correctlyAnswered;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinColumn(name = "game_results_id")
    private GameResults gameResults;

    /* Key vesen
    @EmbeddedId
    private CustomKey id;
    */

    public Question(){}

    public Question(String question, String correctAnswer, String category) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.category = category;
        // Key vesen
        // this.id = id;
        this.correctlyAnswered = false;
    }

    /*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setCorrectlyAnswered(Boolean correctlyAnswered) {
        this.correctlyAnswered = correctlyAnswered;
    }

    public Boolean getCorrectlyAnswered() {
        return correctlyAnswered;
    }

    public void setGameStats(GameResults gameResults) {
        this.gameResults = gameResults;
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        return id != null && id.equals(((Question) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
