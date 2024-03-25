package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class CompletionFeedbacks {

    private Long feedback_id;
    private String text;

    // Constructors
    public CompletionFeedbacks() {
    }

    public CompletionFeedbacks(Long feedback_id, String text) {
        this.feedback_id = feedback_id;
        this.text = text;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedback_id;
    }

    public void setFeedbackId(Long feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // toString method
    @Override
    public String toString() {
        return "CompletionFeedbacks{" +
                "feedback_id=" + feedback_id +
                ", text='" + text + '\'' +
                '}';
    }
}
