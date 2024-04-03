package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Answer {

    private Long answerId;
    private String answerText;

    // Constructors
    public Answer() {
    }

    public Answer(Long answerId, String answerText) {
        this.answerId = answerId;
        this.answerText = answerText;
    }

    // Getters and Setters
    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answerText='" + answerText + 
                '}';
    }
}
