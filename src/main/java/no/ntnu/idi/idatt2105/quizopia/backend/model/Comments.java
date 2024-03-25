package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Comments {

    private Long comment_id;
    private String text;
    private LocalDateTime createdAt;
    private Long user_id; // Foreign key reference to Users
    private Long quiz_id; // Foreign key reference to Quiz

    // Constructors
    public Comments() {
    }

    public Comments(Long comment_id, String text, LocalDateTime createdAt, Long user_id, Long quiz_id) {
        this.comment_id = comment_id;
        this.text = text;
        this.createdAt = createdAt;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
    }

    // Getters and Setters
    public Long getCommentId() {
        return comment_id;
    }

    public void setCommentId(Long comment_id) {
        this.comment_id = comment_id;
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
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "comment_id=" + comment_id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", user_id=" + user_id +
                ", quiz_id=" + quiz_id +
                '}';
    }
}
