package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class AnswersDto {

    private Long answer_id;
    private String answerText;
    private Boolean isCorrect;

    // Constructors
    public AnswersDto() {
    }

    public AnswersDto(String answerText, Boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public AnswersDto(Long answer_id, String answerText, Boolean isCorrect) {
        this.answer_id = answer_id;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Long answer_id) {
        this.answer_id = answer_id;
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
        return "AnswersDto{" +
                "answer_id='" + answer_id + '\'' +
                "answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
