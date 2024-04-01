package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class AnswersDto {

    private Long answer_id;
    private String answerText;
    private Long media_id;

    // Constructors
    public AnswersDto() {
    }

    public AnswersDto(Long answer_id, String answerText, Long media_id) {
        this.answer_id = answer_id;
        this.answerText = answerText;
        this.media_id = media_id;
    }

    // Getters and Setters
    public Long getAnswerId() {
        return answer_id;
    }

    public void setAnswerId(Long answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Long getMediaId() {
        return media_id;
    }

    public void setMediaId(Long media_id) {
        this.media_id = media_id;
    }
}
