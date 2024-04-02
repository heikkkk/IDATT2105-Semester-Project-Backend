package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class AnswerDto {

    private Long answerId;
    private String answerText;
    private Boolean isCorrect;

    // Constructors
    public AnswerDto() {
    }

    public AnswerDto(String answerText, Boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public AnswerDto(Long answerId, String answerText, Boolean isCorrect) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Long getanswerId() {
        return answerId;
    }

    public void setanswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "answerId='" + answerId + '\'' +
                "answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
