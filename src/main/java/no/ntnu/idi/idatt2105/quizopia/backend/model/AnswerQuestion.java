package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class AnswerQuestion {

    private Long questionId; // Foreign key reference to Question
    private Long answerId;  // Foreign key reference to Answer
    private Boolean isCorrect;

    // Constructors
    public AnswerQuestion() {
    }

    public AnswerQuestion(Long questionId, Long answerId, Boolean isCorrect) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "AnswerQuestion{" +
                "questionId=" + questionId +
                ", answerId=" + answerId +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
