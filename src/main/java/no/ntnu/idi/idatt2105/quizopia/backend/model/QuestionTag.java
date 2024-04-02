package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuestionTag {

    private Long tagId; // Foreign key reference to Tag
    private Long questionId; // Foreign key reference to Question

    // Constructors
    public QuestionTag() {
    }

    public QuestionTag(Long tagId, Long questionId) {
        this.tagId = tagId;
        this.questionId = questionId;
    }

    // Getters and Setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuestionTag{" +
                "tagId=" + tagId +
                ", questionId=" + questionId +
                '}';
    }
}
