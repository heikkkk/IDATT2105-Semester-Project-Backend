package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class AnswersDto {

    private String answerText;
    private Boolean isCorrect;

    // Constructors
    public AnswersDto() {
    }

    public AnswersDto(String answerText, Boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
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
        return "AnswersDto{" +
                "answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
