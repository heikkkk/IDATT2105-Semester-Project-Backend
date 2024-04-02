package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Comment {

    private Long commentId;
    private String text;
    private LocalDateTime createdAt;
    private Long userId; // Foreign key reference to Users
    private Long quizId; // Foreign key reference to Quiz

    // Constructors
    public Comment() {
    }

    public Comment(Long comment_id, String text, LocalDateTime createdAt, Long userId, Long quizId) {
        this.commentId = comment_id;
        this.text = text;
        this.createdAt = createdAt;
        this.userId = userId;
        this.quizId = quizId;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", quizId=" + quizId +
                '}';
    }
}
