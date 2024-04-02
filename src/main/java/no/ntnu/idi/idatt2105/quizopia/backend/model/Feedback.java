package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Feedback {

    private Long feedbackId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long userId; // Foreign key reference to Users

    // Constructors
    public Feedback() {
    }

    public Feedback(Long feedbackId, String title, String content, LocalDateTime createdAt, Long userId) {
        this.feedbackId = feedbackId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Feedbacks{" +
                "feedbackId=" + feedbackId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
