package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class CompletionFeedback {

    private Long feedbackId;
    private String text;

    // Constructors
    public CompletionFeedback() {
    }

    public CompletionFeedback(Long feedbackId, String text) {
        this.feedbackId = feedbackId;
        this.text = text;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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
        return "CompletionFeedback{" +
                "feedbackId=" + feedbackId +
                ", text='" + text + '\'' +
                '}';
    }
}
