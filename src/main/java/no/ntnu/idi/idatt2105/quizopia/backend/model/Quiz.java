package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Quiz {

    private Long quiz_id;
    private String title;
    private String description;
    private int timeLeft;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long template_id; // Foreign key reference to Templates
    private Long category_id; // Foreign key reference to Categories 

    // Constructors
    public Quiz() {
    }

    public Quiz(Long quiz_id, String title, String description, int timeLeft, boolean isPublic,
                LocalDateTime createdAt, Long template_id, Long category_id) {
        this.quiz_id = quiz_id;
        this.title = title;
        this.description = description;
        this.timeLeft = timeLeft;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.template_id = template_id;
        this.category_id = category_id;
    }

    // Getters and Setters
    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quizId) {
        this.quiz_id = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTemplateId() {
        return template_id;
    }

    public void setTemplateId(Long template_id) {
        this.template_id = template_id;
    }

    public Long getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Long category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quiz_id=" + quiz_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timeLeft=" + timeLeft +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", template_id=" + template_id +
                ", category_id=" + category_id +
                '}';
    }
}
