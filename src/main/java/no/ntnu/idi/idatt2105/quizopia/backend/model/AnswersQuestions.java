package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class AnswersQuestions {

    private Long question_id; // Foreign key reference to Questions
    private Long answer_id;  // Foreign key reference to Answers
    private Boolean isCorrect;

    // Constructors
    public AnswersQuestions() {
    }

    public AnswersQuestions(Long question_id, Long answer_id, Boolean isCorrect) {
        this.question_id = question_id;
        this.answer_id = answer_id;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Long question_id) {
        this.question_id = question_id;
    }

    public Long getAnswerId() {
        return answer_id;
    }

    public void setAnswerId(Long answer_id) {
        this.answer_id = answer_id;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "AnswersQuestions{" +
                "question_id=" + question_id +
                ", answer_id=" + answer_id +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
