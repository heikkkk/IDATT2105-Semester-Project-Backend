package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Answers {

    private Long answer_id;
    private String answerText;

    // Constructors
    public Answers() {
    }

    public Answers(Long answer_id, String answerText) {
        this.answer_id = answer_id;
        this.answerText = answerText;
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

    @Override
    public String toString() {
        return "Answers{" +
                "answer_id=" + answer_id +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}
