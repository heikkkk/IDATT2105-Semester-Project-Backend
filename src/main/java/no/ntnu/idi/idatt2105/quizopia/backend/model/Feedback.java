package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Feedback {

    private Long feedback_id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long user_id; // Foreign key reference to Users

    // Constructors
    public Feedback() {
    }

    public Feedback(Long feedback_id, String title, String content, LocalDateTime createdAt, Long user_id) {
        this.feedback_id = feedback_id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user_id = user_id;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedback_id;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedback_id = feedbackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Feedbacks{" +
                "feedback_id=" + feedback_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", user_id=" + user_id +
                '}';
    }
}
