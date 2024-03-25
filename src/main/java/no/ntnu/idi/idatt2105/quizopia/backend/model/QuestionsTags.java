package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuestionsTags {

    private Long tag_id; // Foreign key reference to Tag
    private Long question_id; // Foreign key reference to Question

    // Constructors
    public QuestionsTags() {
    }

    public QuestionsTags(Long tag_id, Long question_id) {
        this.tag_id = tag_id;
        this.question_id = question_id;
    }

    // Getters and Setters
    public Long getTagId() {
        return tag_id;
    }

    public void setTagId(Long tag_id) {
        this.tag_id = tag_id;
    }

    public Long getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Long question_id) {
        this.question_id = question_id;
    }

    @Override
    public String toString() {
        return "QuestionsTags{" +
                "tag_id=" + tag_id +
                ", question_id=" + question_id +
                '}';
    }
}
